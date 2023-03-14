package com.example.watchedapp.data.repositories.watchlist

import com.example.watchedapp.data.models.search.SearchMovieResult
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first

class TestWatchlistRepository : WatchlistRepository {
    private val watchlistResourceFlow: MutableSharedFlow<List<SearchMovieResult>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    val watchlistFlow: Flow<List<SearchMovieResult>> = watchlistResourceFlow.filterNotNull()

    override suspend fun addToWatchlist(item: SearchMovieResult) {
        val updated = watchlistFlow.first()
            .toMutableList()
            .apply { add(item) }
            .distinct()
        watchlistResourceFlow.tryEmit(updated)
    }

    override suspend fun removeFromWatchlist(id: Int) {
        val updated = watchlistFlow.first().filter { it.id != id }
        watchlistResourceFlow.tryEmit(updated)
    }

    override fun getWatchlist(): Flow<List<SearchMovieResult>> {
        return watchlistFlow
    }

    fun setWatchlistResource(watchlist: List<SearchMovieResult>) {
        watchlistResourceFlow.tryEmit(watchlist)
    }
}