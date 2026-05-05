package dev.martinloeseth.watchedapp.data.repositories.watchlist

import dev.martinloeseth.watchedapp.domain.models.Movie
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first

class TestWatchlistRepository : WatchlistRepository {
    private val watchlistResourceFlow: MutableSharedFlow<List<Movie>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    val watchlistFlow: Flow<List<Movie>> = watchlistResourceFlow.filterNotNull()

    override suspend fun addToWatchlist(item: Movie) {
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

    override fun getWatchlist(): Flow<List<Movie>> = watchlistFlow

    fun setWatchlistResource(watchlist: List<Movie>) {
        watchlistResourceFlow.tryEmit(watchlist)
    }
}
