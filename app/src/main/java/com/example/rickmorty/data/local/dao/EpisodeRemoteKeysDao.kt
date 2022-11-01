package com.example.rickmorty.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickmorty.data.local.entities.EpisodeRemoteKeys

@Dao
interface EpisodeRemoteKeysDao {

    @Query("SELECT * FROM EpisodeRemoteKeys WHERE id =:id")
    suspend fun getEpisodeRemoteKeys(id: Int): EpisodeRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllEpisodeRemoteKeys(remoteKeys: List<EpisodeRemoteKeys>)

    @Query("DELETE FROM EpisodeRemoteKeys")
    suspend fun deleteAllEpisodeRemoteKeys()
}