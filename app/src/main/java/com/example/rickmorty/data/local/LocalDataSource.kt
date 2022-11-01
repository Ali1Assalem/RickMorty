package com.example.rickmorty.data.local

import androidx.paging.PagingSource
import com.example.rickmorty.data.entities.CharactersEntity
import com.example.rickmorty.data.entities.Episode
import com.example.rickmorty.data.local.dao.CharactersDao
import com.example.rickmorty.data.local.dao.EpisodeDao
import com.example.rickmorty.data.local.dao.EpisodeRemoteKeysDao
import com.example.rickmorty.data.local.entities.EpisodeRemoteKeys
import com.example.rickmorty.data.local.entities.FavoritesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val charactersDao: CharactersDao,
    private val episodeDao: EpisodeDao,
    private val episodeRemoteKeysDao: EpisodeRemoteKeysDao
){
    suspend fun insertCharacters(charactersEntity: CharactersEntity){
        charactersDao.insertCharactersList(charactersEntity)
    }

    suspend fun insertFavorite(favoritesEntity: FavoritesEntity){
        charactersDao.insertFavorite(favoritesEntity)
    }

    fun readCharacters() : Flow<List<CharactersEntity>>{
        return charactersDao.readAllCharacters()
    }


    fun readFavorites() : Flow<List<FavoritesEntity>> {
        return charactersDao.readFavorites()
    }

    suspend fun deleteFavorite(favoritesEntity: FavoritesEntity){
        charactersDao.deleteFavorite(favoritesEntity)
    }

    suspend fun deleteAllFavorites(){
        charactersDao.deleteAllFavorites()
    }

    //============= EpisodeDAO =============
    fun getEpisodes(): PagingSource<Int, Episode>{
        return episodeDao.getEpisodes()
    }

    suspend fun addEpisodes(episodes: List<Episode>){
        episodeDao.addEpisodes(episodes)
    }

    suspend fun deleteEpisodes(){
        episodeDao.deleteEpisodes()
    }


    // ================= EpisodeRemoteKeysDao =======

    suspend fun getEpisodeRemoteKeys(id: Int):EpisodeRemoteKeys{
        return episodeRemoteKeysDao.getEpisodeRemoteKeys(id)
    }

    suspend fun addAllEpisodeRemoteKeys(remoteKeys: List<EpisodeRemoteKeys>){
        episodeRemoteKeysDao.addAllEpisodeRemoteKeys(remoteKeys)
    }

    suspend fun deleteAllEpisodeRemoteKeys(){
        episodeRemoteKeysDao.deleteAllEpisodeRemoteKeys()
    }
}