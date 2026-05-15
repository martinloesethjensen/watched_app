package dev.martinloeseth.watchedapp.domain.usecases

import dev.martinloeseth.watchedapp.domain.models.ThemeMode
import dev.martinloeseth.watchedapp.domain.repositories.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetThemeModeUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
) {
    operator fun invoke(): Flow<ThemeMode> = userPreferencesRepository.getThemeMode()
}
