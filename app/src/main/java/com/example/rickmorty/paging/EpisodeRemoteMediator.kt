package com.example.rickmorty.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.rickmorty.data.entities.Episode
import com.example.rickmorty.data.local.CharactersDatabase
import com.example.rickmorty.data.local.entities.EpisodeRemoteKeys
import com.example.rickmorty.data.remote.CharacterService
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class EpisodeRemoteMediator @Inject constructor (
    private val api: CharacterService,
    private val database: CharactersDatabase
) : RemoteMediator<Int,Episode>() {


        val episodeDao = database.episodeDao()
        val episodeRemoteKeysDao = database.episodeRemoteKeysDao()

        override suspend fun load(loadType: LoadType, state: PagingState<Int, Episode>): RemoteMediator.MediatorResult {
            return try {

                val currentPage = when (loadType) {
                    LoadType.REFRESH -> {
                        val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                        remoteKeys?.nextPage?.minus(1) ?: 1
                    }
                    LoadType.PREPEND -> {
                        val remoteKeys = getRemoteKeyForFirstItem(state)
                        val prevPage = remoteKeys?.prevPage
                            ?: return MediatorResult.Success(
                                endOfPaginationReached = remoteKeys != null
                            )
                        prevPage
                    }
                    LoadType.APPEND -> {
                        val remoteKeys = getRemoteKeyForLastItem(state)
                        val nextPage = remoteKeys?.nextPage
                            ?: return MediatorResult.Success(
                                endOfPaginationReached = remoteKeys != null
                            )
                        nextPage
                    }
                }

                val response = api.getAllEpisode(currentPage)
                val endOfPaginationReached = response.info.pages == currentPage

                val prevPage = if(currentPage == 1) null else currentPage -1
                val nextPage = if(endOfPaginationReached) null else currentPage + 1

                if(response.results.isNotEmpty()){
                    database.withTransaction {

                        if (loadType == LoadType.REFRESH) {
                            episodeDao.deleteEpisodes()
                            episodeRemoteKeysDao.deleteAllEpisodeRemoteKeys()
                        }


                        val keys = response.results.map { episode ->
                            EpisodeRemoteKeys(
                                id = episode.id,
                                prevPage = prevPage,
                                nextPage = nextPage
                            )
                        }

                        episodeDao.addEpisodes(response.results)
                        episodeRemoteKeysDao.addAllEpisodeRemoteKeys(keys)
                    }
                }
                RemoteMediator.MediatorResult.Success(endOfPaginationReached)
            }
            catch (e: Exception){
                RemoteMediator.MediatorResult.Error(e)
            }
        }

        private suspend fun getRemoteKeyClosestToCurrentPosition(
            state: PagingState<Int, Episode>
        ): EpisodeRemoteKeys? {
            return state.anchorPosition?.let { position ->
                state.closestItemToPosition(position)?.id?.let { id ->
                    episodeRemoteKeysDao.getEpisodeRemoteKeys(id = id)
                }
            }
        }

        private suspend fun getRemoteKeyForFirstItem(
            state: PagingState<Int, Episode>
        ): EpisodeRemoteKeys? {
            return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
                ?.let { episode ->
                    episodeRemoteKeysDao.getEpisodeRemoteKeys(id = episode.id)
                }
        }

        private suspend fun getRemoteKeyForLastItem(
            state: PagingState<Int, Episode>
        ): EpisodeRemoteKeys? {
            return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
                ?.let { episode ->
                    episodeRemoteKeysDao.getEpisodeRemoteKeys(id = episode.id)
                }
        }
}