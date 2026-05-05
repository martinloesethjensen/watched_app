package dev.martinloeseth.watchedapp.domain.usecases

import dev.martinloeseth.watchedapp.data.preferences.ThemeMode
import dev.martinloeseth.watchedapp.data.preferences.UserPreferencesRepository
import javax.inject.Inject

class SetThemeModeUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
) {
    suspend operator fun invoke(mode: ThemeMode) = userPreferencesRepository.setThemeMode(mode)
}
