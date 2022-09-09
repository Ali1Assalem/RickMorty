package com.example.rickmorty.di

import android.content.Context
import androidx.room.Room
import com.example.rickmorty.data.local.CharactersDatabase
import com.example.rickmorty.data.local.dao.CharactersDao
import com.example.rickmorty.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        CharactersDatabase::class.java,
        Constants.DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideDao(database: CharactersDatabase) = database.characterDao()

}