package dev.martinloeseth.watchedapp.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_USER_RATING_AND_WATCH_HISTORY = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Add user_rating to existing watchlist table
        db.execSQL("ALTER TABLE watchlist ADD COLUMN user_rating INTEGER")
        // Create new watch_history table
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS watch_history (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                movie_id INTEGER NOT NULL,
                watched_at INTEGER NOT NULL,
                FOREIGN KEY (movie_id) REFERENCES watchlist(id) ON DELETE CASCADE
            )
        """.trimIndent()
        )
        db.execSQL("CREATE INDEX IF NOT EXISTS index_watch_history_movie_id ON watch_history(movie_id)")
    }
}