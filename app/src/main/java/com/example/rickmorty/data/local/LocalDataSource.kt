package com.example.rickmorty.data.local

import com.example.rickmorty.data.entities.Character
import com.example.rickmorty.data.local.entities.CharactersEntity
import com.example.rickmorty.data.local.dao.CharactersDao
import com.example.rickmorty.data.local.entities.FavoritesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val charactersDao: CharactersDao
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
        return charactersDao.readFavorite()
    }

    suspend fun deleteFavorite(favoritesEntity: FavoritesEntity) {
        charactersDao.deleteFavorite(favoritesEntity)
    }

    suspend fun deleteAllFavorites(){
        charactersDao.deleteAllFavorites()
    }
}