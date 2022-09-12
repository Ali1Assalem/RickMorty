package com.example.rickmorty.ui.characters

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickmorty.adapter.CharactersRowAdapter
import com.example.rickmorty.databinding.FragmentCharactersBinding
import com.example.rickmorty.ui.details.CharacterDetailsFragmentArgs

import com.example.rickmorty.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharactersFragment : Fragment() {

    private lateinit var charactersViewModel: CharactersViewModel
    private val mAdapter by lazy { CharactersRowAdapter() }

    private lateinit var binding: FragmentCharactersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        charactersViewModel = ViewModelProvider(requireActivity()).get(CharactersViewModel::class.java)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCharactersBinding.inflate(layoutInflater)



        setupRecyclerView()
        readDatabase()

        return binding.root
    }

    private fun readDatabase() {
        lifecycleScope.launch{
            charactersViewModel.readAllCharacters.observe(viewLifecycleOwner,{
                    database ->
                if (database.isNotEmpty()){
                    Log.d("CharactersFragment","readDatabase called")
                    mAdapter.setData(database[0].characterList)
                    hideShimmerEffect()
                }else{
                    requestApiData()
                }
            })
        }
    }

    private fun requestApiData(){
        Log.d("CharactersFragment","requestApiData called")

        charactersViewModel.getAllCharacters()
        charactersViewModel.charactersResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadDataFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        }
    }

    private fun loadDataFromCache(){
        lifecycleScope.launch {
            charactersViewModel.readAllCharacters.observe(viewLifecycleOwner, { database ->
                if (database.isNotEmpty()){
                    mAdapter.setData(database[0].characterList)
                }
            })
        }
    }


    private fun setupRecyclerView() {
        binding.recyclerviewCharacter.adapter = mAdapter
        binding.recyclerviewCharacter.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    private fun showShimmerEffect() {
        binding.recyclerviewCharacter.showShimmer()
    }

    private fun hideShimmerEffect() {
        binding.recyclerviewCharacter.hideShimmer()
    }

}