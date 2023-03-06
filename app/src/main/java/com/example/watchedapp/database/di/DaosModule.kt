package com.example.watchedapp.database.di

import com.example.watchedapp.database.WatchedDatabase
import com.example.watchedapp.database.dao.WatchlistDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {
    @Provides
    fun providesWatchlistDao(
        database: WatchedDatabase,
    ): WatchlistDao = database.watchlistDao()
}