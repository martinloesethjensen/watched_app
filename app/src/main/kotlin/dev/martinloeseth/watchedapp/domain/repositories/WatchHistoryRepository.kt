package dev.martinloeseth.watchedapp.domain.repositories

import dev.martinloeseth.watchedapp.domain.models.WatchEntry
import kotlinx.coroutines.flow.Flow

interface WatchHistoryRepository {
    fun getWatchHistory(movieId: Int): Flow<List<WatchEntry>>
    fun getWatchedMovieIds(): Flow<Set<Int>>
    suspend fun logWatched(movieId: Int, watchedAt: Long? = null)
    suspend fun deleteEntry(id: Int)
}