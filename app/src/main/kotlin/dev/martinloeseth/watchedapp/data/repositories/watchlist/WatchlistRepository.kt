package dev.martinloeseth.watchedapp.data.repositories.watchlist

import dev.martinloeseth.watchedapp.database.dao.WatchlistDao
import dev.martinloeseth.watchedapp.database.models.WatchlistEntity
import dev.martinloeseth.watchedapp.database.models.asEntity
import dev.martinloeseth.watchedapp.database.models.asExternalModel
import dev.martinloeseth.watchedapp.domain.models.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface WatchlistRepository {
    suspend fun addToWatchlist(item: Movie)
    suspend fun removeFromWatchlist(id: Int)
    fun getWatchlist(): Flow<List<Movie>>
}

class LocalWatchlistRepository @Inject constructor(
    private val watchlistDao: WatchlistDao,
) : WatchlistRepository {
    override suspend fun addToWatchlist(item: Movie) =
        watchlistDao.upsert(item.asEntity())

    override suspend fun removeFromWatchlist(id: Int) = watchlistDao.deleteById(id)

    override fun getWatchlist(): Flow<List<Movie>> =
        watchlistDao.getAll().map { it.map(WatchlistEntity::asExternalModel) }
}
