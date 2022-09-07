package com.example.rickmorty.ui.characters

import android.annotation.SuppressLint
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickmorty.R
import com.example.rickmorty.adapter.CharactersRowAdapter
import com.example.rickmorty.databinding.ActivityMainBinding
import com.example.rickmorty.databinding.FragmentCharactersBinding
import com.example.rickmorty.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@RequiresApi(Build.VERSION_CODES.M)
@AndroidEntryPoint
class CharactersFragment : Fragment() {

    private lateinit var charactersViewModel: CharactersViewModel
    private val mAdapter by lazy { CharactersRowAdapter() }

    lateinit var binding: FragmentCharactersBinding

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
        getAllCharacters()

        return binding.root
    }

    private fun getAllCharacters(){
        charactersViewModel.getAllCharacters()
        charactersViewModel.charactersResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
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