package com.example.rickmorty.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.rickmorty.data.entities.CharactersEntity
import com.example.rickmorty.data.entities.Episode
import com.example.rickmorty.data.local.dao.CharactersDao
import com.example.rickmorty.data.local.dao.EpisodeDao
import com.example.rickmorty.data.local.dao.EpisodeRemoteKeysDao
import com.example.rickmorty.data.local.entities.EpisodeRemoteKeys
import com.example.rickmorty.data.local.entities.FavoritesEntity

@Database(
    entities = [CharactersEntity::class,FavoritesEntity::class,Episode::class,EpisodeRemoteKeys::class],
    version = 3 ,
    exportSchema = false
)
@TypeConverters(CharactersTypeConverter::class)
abstract class CharactersDatabase : RoomDatabase() {
    abstract fun characterDao():CharactersDao
    abstract fun episodeDao() : EpisodeDao
    abstract fun episodeRemoteKeysDao() : EpisodeRemoteKeysDao
}