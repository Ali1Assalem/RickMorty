package com.example.rickmorty.data.remote

import androidx.paging.*
import com.example.rickmorty.data.entities.Episode
import com.example.rickmorty.data.local.CharactersDatabase
import com.example.rickmorty.paging.EpisodeRemoteMediator
import com.example.rickmorty.paging.EpisodesPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteDataSource @Inject constructor (
    private val characterService: CharacterService,
    private val charactersDatabase: CharactersDatabase
) {

    private val episodeDao = charactersDatabase.episodeDao()

    suspend fun getAllCharacters() = characterService.getAllCharacters()
    suspend fun getCharacter(id:Int) = characterService.getCharacter(id)
    suspend fun searchCharacter(name:String) = characterService.searchCharacter(name)

    fun getAllEpisode(): Flow<PagingData<Episode>> {
        val pagingSourceFactory = {episodeDao.getEpisodes()}
        return Pager(
            config = PagingConfig(
                pageSize = 16,
                prefetchDistance = 2,
                initialLoadSize = 24,
                enablePlaceholders = false
            ),
            //pagingSourceFactory = { EpisodesPagingSource(characterService) }
            remoteMediator = EpisodeRemoteMediator(characterService, charactersDatabase),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}