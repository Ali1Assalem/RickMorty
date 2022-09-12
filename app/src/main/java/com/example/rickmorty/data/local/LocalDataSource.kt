package com.example.rickmorty.data.local

import com.example.rickmorty.data.entities.CharactersEntity
import com.example.rickmorty.data.local.dao.CharactersDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val charactersDao: CharactersDao
){
    suspend fun insertCharacters(charactersEntity: CharactersEntity){
        charactersDao.insertCharactersList(charactersEntity)
    }

    fun readCharacters() : Flow<List<CharactersEntity>>{
        return charactersDao.readAllCharacters()
    }
}