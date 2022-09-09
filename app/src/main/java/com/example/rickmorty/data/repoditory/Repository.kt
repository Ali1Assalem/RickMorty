package com.example.rickmorty.data.repoditory

import com.example.rickmorty.data.local.LocalDataSource
import com.example.rickmorty.data.remote.CharacterRemoteDataSource
import javax.inject.Inject

class Repository @Inject constructor(
    private val characterRemoteDataSource: CharacterRemoteDataSource,
    private val localDataSource: LocalDataSource
) {
    val remote = characterRemoteDataSource
    val local = localDataSource
}