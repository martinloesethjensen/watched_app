package com.example.watchedapp.data.repositories.watchlist

import com.example.watchedapp.data.models.search.SearchMovieResult
import com.example.watchedapp.data.models.search.asEntity
import com.example.watchedapp.database.dao.WatchlistDao
import com.example.watchedapp.database.models.WatchlistEntity
import com.example.watchedapp.database.models.asExternalModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface WatchlistRepository {
    suspend fun addToWatchlist(item: SearchMovieResult)

    suspend fun removeFromWatchlist(id: Int)

    fun getWatchlist(): Flow<List<SearchMovieResult>>
}

class LocalWatchlistRepository @Inject constructor(
    private val watchlistDao: WatchlistDao,
) : WatchlistRepository {
    override suspend fun addToWatchlist(item: SearchMovieResult) =
        watchlistDao.upsertWatchlistItem(item.asEntity())

    override suspend fun removeFromWatchlist(id: Int) = watchlistDao.deleteWatchlistItem(id)

    override fun getWatchlist(): Flow<List<SearchMovieResult>> =
        watchlistDao.getWatchlistEntities().map { it.map(WatchlistEntity::asExternalModel) }
}