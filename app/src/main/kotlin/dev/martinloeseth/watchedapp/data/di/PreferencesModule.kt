package dev.martinloeseth.watchedapp.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.martinloeseth.watchedapp.data.preferences.DataStoreUserPreferencesRepository
import dev.martinloeseth.watchedapp.domain.repositories.UserPreferencesRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferencesModule {

    @Binds
    abstract fun bindsUserPreferencesRepository(
        impl: DataStoreUserPreferencesRepository,
    ): UserPreferencesRepository

    companion object {
        @Provides
        @Singleton
        fun providesDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
            PreferenceDataStoreFactory.create(
                produceFile = { context.preferencesDataStoreFile("user_preferences") }
            )
    }
}
