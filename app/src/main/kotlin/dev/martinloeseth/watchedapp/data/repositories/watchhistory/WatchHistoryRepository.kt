package dev.martinloeseth.watchedapp.data.repositories.watchhistory

import dev.martinloeseth.watchedapp.database.dao.WatchHistoryDao
import dev.martinloeseth.watchedapp.database.models.WatchHistoryEntity
import dev.martinloeseth.watchedapp.database.models.asModel
import dev.martinloeseth.watchedapp.domain.models.WatchEntry
import dev.martinloeseth.watchedapp.domain.repositories.WatchHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalWatchHistoryRepository @Inject constructor(
    private val watchHistoryDao: WatchHistoryDao,
) : WatchHistoryRepository {
    override fun getWatchHistory(movieId: Int): Flow<List<WatchEntry>> =
        watchHistoryDao.getForMovie(movieId).map { it.map(WatchHistoryEntity::asModel) }

    override fun getWatchedMovieIds(): Flow<Set<Int>> = watchHistoryDao.getWatchedMovieIds()

    override suspend fun logWatched(movieId: Int, watchedAt: Long?) = watchHistoryDao.insert(
        WatchHistoryEntity(
            movieId = movieId, watchedAt = watchedAt ?: System.currentTimeMillis(),
        )
    )

    override suspend fun deleteEntry(id: Int) = watchHistoryDao.deleteById(id)
}