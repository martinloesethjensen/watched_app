package dev.martinloeseth.watchedapp.database.di

import dev.martinloeseth.watchedapp.database.WatchedDatabase
import dev.martinloeseth.watchedapp.database.dao.WatchlistDao
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