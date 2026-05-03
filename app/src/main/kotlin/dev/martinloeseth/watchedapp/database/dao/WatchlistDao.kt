package dev.martinloeseth.watchedapp.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import dev.martinloeseth.watchedapp.database.models.WatchlistEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchlistDao {
    @Query("SELECT * FROM watchlist")
    fun getAll(): Flow<List<WatchlistEntity>>

    @Upsert
    suspend fun upsert(watchlistItem: WatchlistEntity)

    @Query(
        value = """
            DELETE FROM watchlist
            WHERE id = :id
        """
    )
    suspend fun deleteById(id: Int)
}