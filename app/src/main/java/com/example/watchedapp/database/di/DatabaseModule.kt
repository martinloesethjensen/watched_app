package com.example.watchedapp.database.di

import android.content.Context
import androidx.room.Room
import com.example.watchedapp.database.WatchedDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesWatchedDatabase(
        @ApplicationContext context: Context
    ): WatchedDatabase = Room.databaseBuilder(
        context,
        WatchedDatabase::class.java,
        "watched-database"
    ).build()
}