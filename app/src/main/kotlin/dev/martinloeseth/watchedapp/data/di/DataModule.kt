package dev.martinloeseth.watchedapp.data.di

import dev.martinloeseth.watchedapp.data.repositories.config.ConfigRepository
import dev.martinloeseth.watchedapp.data.repositories.config.RemoteConfigRepository
import dev.martinloeseth.watchedapp.data.repositories.search.RemoteSearchRepository
import dev.martinloeseth.watchedapp.data.repositories.search.SearchRepository
import dev.martinloeseth.watchedapp.data.repositories.watchlist.LocalWatchlistRepository
import dev.martinloeseth.watchedapp.data.repositories.watchlist.WatchlistRepository
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