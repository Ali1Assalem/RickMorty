package com.example.rickmorty.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.rickmorty.utils.Constants

@Entity(tableName = "EpisodeRemoteKeys")
data class EpisodeRemoteKeys(

    @PrimaryKey(autoGenerate = false)
    val id: Int,

    val prevPage: Int?,
    val nextPage: Int?
)