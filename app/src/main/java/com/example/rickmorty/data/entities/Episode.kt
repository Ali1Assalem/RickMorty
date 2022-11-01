package com.example.rickmorty.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Episode")
data class Episode (
    @PrimaryKey(autoGenerate = false)
    val id: Int,

    val name: String,
    val air_date: String,
    //val characters: List<String>,
    val episode: String,
)