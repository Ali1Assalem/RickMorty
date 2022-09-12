package com.example.rickmorty.bindingAdapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.example.rickmorty.data.entities.CharacterList
import com.example.rickmorty.data.local.entities.CharactersEntity
import com.example.rickmorty.utils.NetworkResult

class CharactersBinding {

    companion object {
        @BindingAdapter("readApiResponse", "readDatabase", requireAll = true)
        @JvmStatic
        fun errorImageViewVisibility(
            view: View,
            apiResponse: NetworkResult<CharacterList>?,
            database: List<CharactersEntity>?
        ){
            when (view){
                is ImageView ->{
                    view.isVisible = apiResponse is NetworkResult.Error && database.isNullOrEmpty()
                }
                is TextView ->{
                    view.isVisible = apiResponse is NetworkResult.Error && database.isNullOrEmpty()
                    //view.text = apiResponse?.message.toString()
                }
            }
        }
    }
}