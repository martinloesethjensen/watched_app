package dev.martinloeseth.watchedapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.martinloeseth.watchedapp.database.dao.WatchlistDao
import dev.martinloeseth.watchedapp.database.models.WatchlistEntity
import dev.martinloeseth.watchedapp.database.util.WatchlistConverter

@Database(
    entities = [
        WatchlistEntity::class
    ],
    version = 1
)
@TypeConverters(WatchlistConverter::class)
abstract class WatchedDatabase : RoomDatabase() {
    abstract fun watchlistDao(): WatchlistDao
}