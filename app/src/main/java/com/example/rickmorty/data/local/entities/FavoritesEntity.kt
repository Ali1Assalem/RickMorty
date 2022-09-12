package com.example.rickmorty.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.rickmorty.data.entities.Character
import com.example.rickmorty.utils.Constants

@Entity(tableName = Constants.FAVORITES_TABLE)
class FavoritesEntity (
    @PrimaryKey(autoGenerate = true)
    var id:Int ,

    var character: Character
)