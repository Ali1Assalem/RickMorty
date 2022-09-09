package com.example.rickmorty.bindingAdapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.example.rickmorty.R
import com.example.rickmorty.data.entities.Character
import com.example.rickmorty.ui.characters.CharactersFragmentDirections


class CharactersRowBinding {

    companion object {

        @BindingAdapter("onCharacterClickListener")
        @JvmStatic
        fun onCharacterClickListener(characterRowLayout: ConstraintLayout, character: Character){
            characterRowLayout.setOnClickListener{
                try {
                    val action =
                        CharactersFragmentDirections.actionCharactersFragmentToCharacterDetailsFragment(character)
                    characterRowLayout.findNavController().navigate(action)
                }catch (e:Exception) {
                    Log.d("s",e.message.toString())
                }
            }
        }


        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, imageUrl: String) {
            imageView.load(imageUrl) {
                crossfade(600)
                transformations(CircleCropTransformation())
                transformations(RoundedCornersTransformation(25f))
                placeholder(R.drawable.ic_error_placeholder)
                error(R.drawable.ic_error_placeholder)
            }
        }


        @BindingAdapter("applyStatusColor")
        @JvmStatic
        fun applyStatusColor(view: View, status: String) {
            if (status == "Alive") {
                when (view) {
                    is TextView -> {
                        view.setTextColor(
                            ContextCompat.getColor(
                                view.context,
                                R.color.green
                            )
                        )
                    }
                    is ImageView -> {
                        view.setColorFilter(
                            ContextCompat.getColor(
                                view.context,
                                R.color.green
                            )
                        )
                    }
                }

            } else {
                when (view) {
                    is TextView -> {
                        view.setTextColor(
                            ContextCompat.getColor(
                                view.context,
                                R.color.red
                            )
                        )
                    }

                    is ImageView -> {
                        view.setColorFilter(
                            ContextCompat.getColor(
                                view.context,
                                R.color.red
                            )
                        )
                    }
                }
            }


        }
    }
}