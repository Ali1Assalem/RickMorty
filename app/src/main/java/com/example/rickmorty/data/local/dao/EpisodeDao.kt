package com.example.rickmorty.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickmorty.data.entities.Episode

@Dao
interface EpisodeDao {
    @Query("SELECT * FROM Episode")
    fun getEpisodes(): PagingSource<Int, Episode>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addEpisodes(episodes: List<Episode>)

    @Query("DELETE FROM Episode")
    suspend fun deleteEpisodes()
}