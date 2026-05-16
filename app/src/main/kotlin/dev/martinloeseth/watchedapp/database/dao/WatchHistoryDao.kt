package dev.martinloeseth.watchedapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import dev.martinloeseth.watchedapp.database.models.WatchHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchHistoryDao {
    @Insert
    suspend fun insert(entry: WatchHistoryEntity)

    @Query("SELECT * FROM watch_history WHERE movie_id = :movieId ORDER BY watched_at DESC")
    fun getForMovie(movieId: Int): Flow<List<WatchHistoryEntity>>

    @Query("SELECT DISTINCT movie_id from watch_history")
    fun getWatchedMovieIds(): Flow<Set<Int>>

    @Query("DELETE FROM watch_history WHERE id = :id")
    suspend fun deleteById(id: Int)
}