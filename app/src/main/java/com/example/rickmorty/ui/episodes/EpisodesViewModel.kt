package com.example.rickmorty.ui.episodes

import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickmorty.data.entities.Episode
import com.example.rickmorty.data.repoditory.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class EpisodesViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val list: Flow<PagingData<Episode>> = repository.remote.getAllEpisode().cachedIn(viewModelScope)

}