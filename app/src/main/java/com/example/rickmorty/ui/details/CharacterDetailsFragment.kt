package com.example.rickmorty.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import androidx.navigation.navArgs
import coil.load
import com.example.rickmorty.R
import com.example.rickmorty.data.entities.Character
import com.example.rickmorty.databinding.FragmentCharacterDetailsBinding
import com.example.rickmorty.databinding.FragmentCharactersBinding
import com.example.rickmorty.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel

@AndroidEntryPoint
class CharacterDetailsFragment : Fragment() {

    private val args : CharacterDetailsFragmentArgs by navArgs()

    lateinit var binding: FragmentCharacterDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding =  FragmentCharacterDetailsBinding.inflate(layoutInflater)


        val myByndle : Character = args.result

        setUpDetail(myByndle)

        return binding.root
    }

    fun setUpDetail(character : Character){
        binding.image.load(character.image){
            error(R.drawable.ic_error_placeholder)
            placeholder(R.drawable.ic_error_placeholder)
        }
        binding.name.text = character.name
        binding.status.text = character.status
        binding.type.text = character.type
        binding.species.text = character.species
        binding.gender.text = character.gender
        binding.created.text = character.created
    }

}