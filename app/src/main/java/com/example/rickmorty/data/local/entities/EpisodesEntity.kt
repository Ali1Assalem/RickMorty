package com.example.rickmorty.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.rickmorty.data.entities.Character
import com.example.rickmorty.data.entities.Episode
import com.example.rickmorty.data.entities.EpisodesList
import com.example.rickmorty.utils.Constants

@Entity(tableName = Constants.EPISODES_TABLE)
class EpisodesEntity (
    @PrimaryKey(autoGenerate = true)
    var id:Int ,
    var episodesList: EpisodesList
)