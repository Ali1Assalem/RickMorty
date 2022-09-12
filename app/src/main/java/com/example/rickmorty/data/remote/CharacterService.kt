package com.example.rickmorty.data.remote

import com.example.rickmorty.data.entities.Character
import com.example.rickmorty.data.entities.CharacterList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterService {

    @GET("character")
    suspend fun getAllCharacters() : Response<CharacterList>

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): Response<Character>

    @GET("character")
    suspend fun searchCharacter(@Query("name") name: String): Response<CharacterList>


//    @GET("location")
//    suspend fun getAllLocation(@Query("page") page: Int? = null): LocationDto
//
//    @GET("location/{id}")
//    suspend fun getLocation(@Path("id") locationId: Int): LocationResults
//
//    @GET("episode")
//    suspend fun getAllEpisode(@Query("page") page: Int? = null): EpisodeDto
//
//    @GET("episode/{id}")
//    suspend fun getEpisodeById(@Path("id") episodeId: Int): EpisodeResult

}