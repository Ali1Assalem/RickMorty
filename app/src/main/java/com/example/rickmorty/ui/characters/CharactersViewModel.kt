package com.example.rickmorty.ui.characters

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.example.rickmorty.data.entities.CharacterList
import com.example.rickmorty.data.repoditory.Repository
import com.example.rickmorty.utils.NetworkResult
import dagger.hilt.android.internal.Contexts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.M)
@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    var charactersResponse: MutableLiveData<NetworkResult<CharacterList>> = MutableLiveData()

    fun getAllCharacters  () {
        viewModelScope.launch {
            getCharactersSafeCall()
        }
    }

    private suspend fun getCharactersSafeCall(){
        charactersResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.getAllCharacters()
                charactersResponse.value = handleGetCharachtersResponse(response)
            }catch (e:Exception){
                charactersResponse.value = NetworkResult.Error("Charachters Not Found.")
            }
        }else {
            charactersResponse.value = NetworkResult.Error("No Internet Connection.")
        }
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