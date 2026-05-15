package dev.martinloeseth.watchedapp.domain.usecases

import dev.martinloeseth.watchedapp.domain.models.ThemeMode
import dev.martinloeseth.watchedapp.domain.repositories.UserPreferencesRepository
import javax.inject.Inject

class SetThemeModeUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
) {
    suspend operator fun invoke(mode: ThemeMode) = userPreferencesRepository.setThemeMode(mode)
}
