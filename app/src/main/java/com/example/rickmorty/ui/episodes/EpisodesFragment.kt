package com.example.rickmorty.ui.episodes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickmorty.adapter.EpisodesPagingAdapter
import com.example.rickmorty.paging.EpisodeLoaderAdapter
import com.example.rickmorty.ui.characters.CharactersViewModel
import com.example.rickmorty.utils.Util
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import android.os.Parcelable
import android.widget.Toast
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.example.rickmorty.databinding.FragmentEpisodesBinding
import kotlinx.coroutines.flow.collectLatest


@OptIn(ExperimentalPagingApi::class)
@AndroidEntryPoint
class EpisodesFragment : Fragment() {

    private var _binding: FragmentEpisodesBinding? = null
    private val binding get() = _binding!!

    private lateinit var charactersViewModel: CharactersViewModel
    private lateinit var episodesViewModel: EpisodesViewModel

    lateinit var adapter: EpisodesPagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        charactersViewModel = ViewModelProvider(requireActivity()).get(CharactersViewModel::class.java)
        episodesViewModel = ViewModelProvider(requireActivity()).get(EpisodesViewModel::class.java)

        adapter=EpisodesPagingAdapter()

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEpisodesBinding.inflate(layoutInflater)

        binding.lifecycleOwner = this
        binding.characterViewModel = charactersViewModel

        setupRecyclerView()


        lifecycleScope.launch {
            adapter.loadStateFlow.collect {
                Util.loadingState(
                    it,
                    binding.lottieAnimationView,
                    binding.shimmerLoading,
                    binding.refreshBtn,
                    true,
                    adapter
                )
            }
        }

        lifecycleScope.launch{
            episodesViewModel.list.collectLatest {
                adapter.submitData(lifecycle, it)
            }
        }

        binding.refreshBtn.setOnClickListener {
            adapter.retry()
        }

        return binding.root
    }


    private fun setupRecyclerView() {

        binding.recyclerviewEpisodes.adapter = adapter.withLoadStateHeaderAndFooter(
            header = EpisodeLoaderAdapter { adapter.retry() },
            footer = EpisodeLoaderAdapter { adapter.retry() }
        )

        binding.recyclerviewEpisodes.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerviewEpisodes.setHasFixedSize(true)
        binding.recyclerviewEpisodes.scrollToPosition(0)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}