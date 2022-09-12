package com.example.rickmorty.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.rickmorty.data.local.entities.CharactersEntity
import com.example.rickmorty.data.local.dao.CharactersDao
import com.example.rickmorty.data.local.entities.FavoritesEntity

@Database(
    entities = [CharactersEntity::class , FavoritesEntity::class],
    version = 1 ,
    exportSchema = false
)
@TypeConverters(CharactersTypeConverter::class)
abstract class CharactersDatabase : RoomDatabase() {
    abstract fun characterDao():CharactersDao
}