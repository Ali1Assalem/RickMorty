package com.example.rickmorty.ui.episodes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickmorty.R
import com.example.rickmorty.adapter.CharactersRowAdapter
import com.example.rickmorty.adapter.EpisodesRowAdapter
import com.example.rickmorty.databinding.FragmentEpisodesBinding
import com.example.rickmorty.ui.characters.CharactersViewModel
import com.example.rickmorty.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EpisodesFragment : Fragment() {

    private lateinit var binding : FragmentEpisodesBinding
    private lateinit var charactersViewModel: CharactersViewModel
    private lateinit var episodesViewModel: EpisodesViewModel

    private val mAdapter by lazy { EpisodesRowAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        charactersViewModel = ViewModelProvider(requireActivity()).get(CharactersViewModel::class.java)
        episodesViewModel = ViewModelProvider(requireActivity()).get(EpisodesViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEpisodesBinding.inflate(layoutInflater)

        binding.lifecycleOwner = this
        binding.characterViewModel = charactersViewModel
        binding.episodesViewModel = episodesViewModel

        setupRecyclerView()

        readEpisodesDatabase()

        return binding.root
    }


    private fun readEpisodesDatabase() {
        lifecycleScope.launch{
            episodesViewModel.readEpisodes.observe(viewLifecycleOwner,{
                    database ->
                if (database.isNotEmpty()){
                    Log.d("EpisodesFragment","readDatabase called")
                    mAdapter.setData(database[0].episodesList)
                    hideShimmerEffect()
                }else{
                    requestApiEpisodesData()
                }
            })
        }
    }


    private fun requestApiEpisodesData(){
        Log.d("EpisodesFragment","requestApiData called")

        episodesViewModel.getAllEpisodes()
        episodesViewModel.episodesResponse.observe(viewLifecycleOwner) { response ->
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
            episodesViewModel.readEpisodes.observe(viewLifecycleOwner, { database ->
                if (database.isNotEmpty()){
                    mAdapter.setData(database[0].episodesList)
                }
            })
        }
    }


    private fun setupRecyclerView() {
        binding.recyclerviewEpisodes.adapter = mAdapter
        binding.recyclerviewEpisodes.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    private fun showShimmerEffect() {
        binding.recyclerviewEpisodes.showShimmer()
    }

    private fun hideShimmerEffect() {
        binding.recyclerviewEpisodes.hideShimmer()
    }

}