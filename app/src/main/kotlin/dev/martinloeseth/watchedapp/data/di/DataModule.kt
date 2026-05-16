package dev.martinloeseth.watchedapp.data.di

import dev.martinloeseth.watchedapp.data.repositories.config.RemoteConfigRepository
import dev.martinloeseth.watchedapp.data.repositories.moviedetails.RemoteMovieDetailsRepository
import dev.martinloeseth.watchedapp.data.repositories.search.RemoteSearchRepository
import dev.martinloeseth.watchedapp.data.repositories.watchlist.LocalWatchlistRepository
import dev.martinloeseth.watchedapp.domain.repositories.ConfigRepository
import dev.martinloeseth.watchedapp.domain.repositories.MovieDetailsRepository
import dev.martinloeseth.watchedapp.domain.repositories.SearchRepository
import dev.martinloeseth.watchedapp.domain.repositories.WatchlistRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.martinloeseth.watchedapp.data.repositories.watchhistory.LocalWatchHistoryRepository
import dev.martinloeseth.watchedapp.domain.repositories.WatchHistoryRepository

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
        watchlistRepository: LocalWatchlistRepository,
    ): WatchlistRepository

    @Binds
    fun bindsMovieDetailsRepository(
        movieDetailsRepository: RemoteMovieDetailsRepository,
    ): MovieDetailsRepository

    @Binds
    fun bindsWatchHistoryRepository(
        watchHistoryRepository: LocalWatchHistoryRepository,
    ): WatchHistoryRepository
}
