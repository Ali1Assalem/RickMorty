package com.example.rickmorty.data.repoditory

import com.example.rickmorty.data.remote.CharacterRemoteDataSource
import javax.inject.Inject

class Repository @Inject constructor(
    private val characterRemoteDataSource: CharacterRemoteDataSource
) {
    suspend fun getAllCharacters() = characterRemoteDataSource.getAllCharacters()
    suspend fun getCharachter(id:Int) = characterRemoteDataSource.getCharacter(id)

}