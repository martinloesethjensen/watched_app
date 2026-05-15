package dev.martinloeseth.watchedapp.domain.repositories

import dev.martinloeseth.watchedapp.domain.models.Movie
import kotlinx.coroutines.flow.Flow

interface WatchlistRepository {
    suspend fun addToWatchlist(item: Movie)
    suspend fun removeFromWatchlist(id: Int)
    fun getWatchlist(): Flow<List<Movie>>
}
