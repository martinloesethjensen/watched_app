package dev.martinloeseth.watchedapp.domain.usecases

import dev.martinloeseth.watchedapp.data.preferences.ThemeMode
import dev.martinloeseth.watchedapp.data.preferences.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetThemeModeUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
) {
    operator fun invoke(): Flow<ThemeMode> = userPreferencesRepository.getThemeMode()
}
