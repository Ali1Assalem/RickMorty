package com.example.rickmorty.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.rickmorty.data.entities.CharacterList
import com.example.rickmorty.utils.Constants

@Entity(tableName = Constants.CHARACTERS_TABLE)
class CharactersEntity (
    var characterList: CharacterList
  ){
    @PrimaryKey(autoGenerate = false)
    var id:Int = 0
}