package dev.martinloeseth.watchedapp.domain.repositories

import dev.martinloeseth.watchedapp.domain.models.ThemeMode
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    fun getThemeMode(): Flow<ThemeMode>
    suspend fun setThemeMode(mode: ThemeMode)
}
