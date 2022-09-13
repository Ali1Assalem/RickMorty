package com.example.rickmorty.data.local.dao

import androidx.room.*
import com.example.rickmorty.data.entities.CharacterList
import com.example.rickmorty.data.entities.CharactersEntity
import com.example.rickmorty.data.local.entities.EpisodesEntity
import com.example.rickmorty.data.local.entities.FavoritesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharactersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharactersList(charactersEntity: CharactersEntity)

    @Query("Select * From characters_table ORDER BY id ASC")
    fun readAllCharacters():Flow<List<CharactersEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favoritesEntity: FavoritesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisode(episodesEntity: EpisodesEntity)

    @Query("SELECT * From episodes_table ORDER BY id ASC")
    fun readEpisodes():Flow<List<EpisodesEntity>>

    @Query("SELECT * From favorites_table ORDER BY id ASC")
    fun readFavorites():Flow<List<FavoritesEntity>>

    @Delete
    suspend fun deleteFavorite(favoritesEntity: FavoritesEntity)

    @Query("DELETE FROM favorites_table")
    suspend fun deleteAllFavorites()
}