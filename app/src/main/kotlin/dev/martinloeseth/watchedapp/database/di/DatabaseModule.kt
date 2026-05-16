package dev.martinloeseth.watchedapp.database.di

import android.content.Context
import androidx.room.Room
import dev.martinloeseth.watchedapp.database.WatchedDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.martinloeseth.watchedapp.database.migrations.MIGRATION_USER_RATING_AND_WATCH_HISTORY
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesWatchedDatabase(
        @ApplicationContext context: Context
    ): WatchedDatabase = Room.databaseBuilder(
        context,
        WatchedDatabase::class.java,
        "watched-database"
    ).addMigrations(MIGRATION_USER_RATING_AND_WATCH_HISTORY).build()
}