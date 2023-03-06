package com.example.watchedapp.data.di

import com.example.watchedapp.data.repositories.config.ConfigRepository
import com.example.watchedapp.data.repositories.config.RemoteConfigRepository
import com.example.watchedapp.data.repositories.search.RemoteSearchRepository
import com.example.watchedapp.data.repositories.search.SearchRepository
import com.example.watchedapp.data.repositories.watchlist.LocalWatchlistRepository
import com.example.watchedapp.data.repositories.watchlist.WatchlistRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsConfigRepository(
        configRepository: RemoteConfigRepository,
    ): ConfigRepository

    @Binds
    fun bindsSearchRepository(
        searchRepository: RemoteSearchRepository,
    ): SearchRepository

    @Binds
    fun bindsWatchlistRepository(
        watchlistRepository: LocalWatchlistRepository
    ): WatchlistRepository
}