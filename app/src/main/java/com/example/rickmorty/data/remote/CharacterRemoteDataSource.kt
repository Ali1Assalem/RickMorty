package com.example.rickmorty.data.remote

import javax.inject.Inject

class CharacterRemoteDataSource @Inject constructor(
    private val characterService: CharacterService
) {

    suspend fun getAllCharacters() = characterService.getAllCharacters()
    suspend fun getCharacter(id:Int) = characterService.getCharacter(id)

}