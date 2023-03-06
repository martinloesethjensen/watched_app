package com.example.watchedapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.watchedapp.database.dao.WatchlistDao
import com.example.watchedapp.database.models.WatchlistEntity
import com.example.watchedapp.database.util.WatchlistConverter

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