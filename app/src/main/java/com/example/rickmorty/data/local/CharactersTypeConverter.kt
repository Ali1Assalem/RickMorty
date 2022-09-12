package com.example.rickmorty.data.local

import androidx.room.TypeConverter
import com.example.rickmorty.data.entities.Character
import com.example.rickmorty.data.entities.CharacterList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CharactersTypeConverter {
    var gson = Gson()

    @TypeConverter
    fun charactersListToString(characterList: CharacterList): String {
        return gson.toJson(characterList)
    }

    @TypeConverter
    fun stringToCharactersList(data : String) : CharacterList {
        val lisType = object : TypeToken<CharacterList>() {}.type
        return gson.fromJson(data,lisType)
    }

    @TypeConverter
    fun characterToString(character:Character) : String {
        return gson.toJson(character)
    }

    @TypeConverter
    fun stringToCharacter(data : String) : Character {
        val lisType = object : TypeToken<Character>() {}.type
        return gson.fromJson(data,lisType)
    }
}