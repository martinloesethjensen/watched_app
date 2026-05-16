package dev.martinloeseth.watchedapp.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import dev.martinloeseth.watchedapp.domain.models.WatchEntry

@Entity(
    tableName = "watch_history",
    foreignKeys = [
        ForeignKey(
            entity = WatchlistEntity::class,
            parentColumns = ["id"],
            childColumns = ["movie_id"],
            onDelete = ForeignKey.CASCADE,
        )
    ]
)
data class WatchHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "movie_id", index = true) val movieId: Int,
    @ColumnInfo(name = "watched_at") val watchedAt: Long,
)

fun WatchHistoryEntity.asExternalModel() = WatchEntry(id, movieId, watchedAt)

fun WatchEntry.asEntity() = WatchHistoryEntity(id, movieId, watchedAt)