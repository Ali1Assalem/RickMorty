package com.example.rickmorty.ui.characters

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.example.rickmorty.data.entities.CharacterList
import com.example.rickmorty.data.entities.CharactersEntity
import com.example.rickmorty.data.repoditory.Repository
import com.example.rickmorty.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {


    //   ROOM

    val readAllCharacters : LiveData<List<CharactersEntity>> = repository.local.readCharacters().asLiveData()

    private fun insertCharacters(charactersEntity: CharactersEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertCharacters(charactersEntity)
        }
    }




    //    RETROFIT

    var charactersResponse: MutableLiveData<NetworkResult<CharacterList>> = MutableLiveData()

    fun getAllCharacters  () {
        viewModelScope.launch(Dispatchers.IO) {
            getCharactersSafeCall()
        }
    }

    private suspend fun getCharactersSafeCall(){
        charactersResponse.postValue( NetworkResult.Loading())
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getAllCharacters()
                charactersResponse.postValue(handleGetCharachtersResponse(response) )

                val charactersList = response.body()
                if (charactersList != null ){
                    offlineCacheCharacters(charactersList)
                }


            }catch (e:Exception){
                charactersResponse.postValue(NetworkResult.Error("Charachters Not Found."))
            }
        }else {
            charactersResponse.postValue( NetworkResult.Error("No Internet Connection."))
        }
    }

    private fun offlineCacheCharacters(charactersList: CharacterList) {
        val charactersEntity = CharactersEntity(charactersList)
        insertCharacters(charactersEntity)
    }


    private fun handleGetCharachtersResponse(response: Response<CharacterList>) : NetworkResult<CharacterList>? {
        when{
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API Key Limited.")
            }
            response.body()!!.results.isNullOrEmpty() -> {
                return NetworkResult.Error("Characters Not Found.")
            }
            response.isSuccessful -> {
                val characters = response.body()
                return NetworkResult.Success(characters!!)
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