package com.example.rickmorty.ui.episodes

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.example.rickmorty.data.entities.Character
import com.example.rickmorty.data.entities.CharacterList
import com.example.rickmorty.data.entities.CharactersEntity
import com.example.rickmorty.data.entities.EpisodesList
import com.example.rickmorty.data.local.entities.EpisodesEntity
import com.example.rickmorty.data.local.entities.FavoritesEntity
import com.example.rickmorty.data.repoditory.Repository
import com.example.rickmorty.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    private val _readEpisodes = MutableSharedFlow<List<EpisodesEntity>>()
    val readEpisodes : SharedFlow<List<EpisodesEntity>> = _readEpisodes

    fun fetchEpisodesFromLocal() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.readEpisodes().collect{
                _readEpisodes.emit(it)
            }
        }
    }

    private var _episodesResponse = MutableSharedFlow<NetworkResult<EpisodesList>>()
    val episodesResponse : SharedFlow<NetworkResult<EpisodesList>> = _episodesResponse

    val characterList = mutableListOf<Character>()

    fun getAllEpisodes  () {
        viewModelScope.launch(Dispatchers.IO) {
            getEpisodesSafeCall()
        }
    }

    fun insertEpisode(episodesEntity: EpisodesEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertEpisode(episodesEntity)
        }
    }

    private suspend fun getEpisodesSafeCall(){
        _episodesResponse.emit(NetworkResult.Loading())
        if (hasInternetConnection()){
            try {
                val response = repository.remote.getAllEpisode()
                _episodesResponse.emit(handleGetEpisodesResponse(response)!!)

                val episodesList = response.body()
                if (episodesList != null ){
                    offlineCacheEpisodes(episodesList)
                }

            }catch (e:Exception){
                _episodesResponse.emit(NetworkResult.Error("Episodes Not Found."))
            }
        }else{
            _episodesResponse.emit(NetworkResult.Error("No Internet Connection."))
        }
    }

    private fun offlineCacheEpisodes(episodesList: EpisodesList) {
        val episodesEntity = EpisodesEntity(1,episodesList)
        insertEpisode(episodesEntity)
    }


    private fun handleGetEpisodesResponse(response: Response<EpisodesList>) : NetworkResult<EpisodesList>? {
        when{
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API Key Limited.")
            }
            response.body()!!.results.isNullOrEmpty() -> {
                return NetworkResult.Error("Episodes Not Found.")
            }
            response.isSuccessful -> {
                val episodes = response.body()
                return NetworkResult.Success(episodes!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }


    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}