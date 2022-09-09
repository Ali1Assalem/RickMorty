package com.example.rickmorty.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickmorty.data.entities.CharacterList
import com.example.rickmorty.data.entities.CharactersEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharactersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharactersList(charactersEntity: CharactersEntity)

    @Query("Select * From characters_table ORDER BY id ASC")
    fun readAllCharacters():Flow<List<CharactersEntity>>
}