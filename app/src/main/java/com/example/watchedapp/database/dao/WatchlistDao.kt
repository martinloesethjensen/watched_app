package com.example.watchedapp.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.watchedapp.database.models.WatchlistEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchlistDao {
    @Query("SELECT * FROM watchlist")
    fun getWatchlistEntities(): Flow<List<WatchlistEntity>>

    @Upsert
    suspend fun upsertWatchlistItem(watchlistItem: WatchlistEntity)

    @Query(
        value = """
            DELETE FROM watchlist
            WHERE id = :id
        """
    )
    suspend fun deleteWatchlistItem(id: Int)
}