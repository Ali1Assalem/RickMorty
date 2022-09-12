package com.example.rickmorty.data.local.dao

import androidx.room.*
import com.example.rickmorty.data.entities.Character
import com.example.rickmorty.data.local.entities.CharactersEntity
import com.example.rickmorty.data.local.entities.FavoritesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharactersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharactersList(charactersEntity: CharactersEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favoritesEntity: FavoritesEntity)

    @Query("Select * From characters_table ORDER BY id ASC")
    fun readAllCharacters():Flow<List<CharactersEntity>>

    @Query("SELECT * From favorites_table ORDER BY id ASC")
    fun readFavorite():Flow<List<FavoritesEntity>>

    @Delete
    suspend fun deleteFavorite(favoritesEntity: FavoritesEntity)

    @Query("DELETE FROM favorites_table")
    suspend fun deleteAllFavorites()
}