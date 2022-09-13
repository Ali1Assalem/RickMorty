package com.example.rickmorty.data.entities

data class Episode (
    val id: Int,
    val name: String,
    val air_date: String,
    val characters: List<String>,
    val episode: String,
)