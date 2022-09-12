package com.example.rickmorty.data.local

import androidx.room.TypeConverter
import com.example.rickmorty.data.entities.CharacterList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CharactersTypeConverter {
    var gson = Gson()

    @TypeConverter
    fun charactersToString(characterList: CharacterList): String {
        return gson.toJson(characterList)
    }

    @TypeConverter
    fun StringToCharacters(data : String) : CharacterList {
        val lisType = object : TypeToken<CharacterList>() {}.type
        return gson.fromJson(data,lisType)
    }
}