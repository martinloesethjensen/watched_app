package dev.martinloeseth.watchedapp.domain.usecases

import dev.martinloeseth.watchedapp.data.models.config.ConfigResult
import dev.martinloeseth.watchedapp.data.repositories.config.ConfigRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetConfigUseCase @Inject constructor(
    private val configRepository: ConfigRepository,
) {
    operator fun invoke(): Flow<ConfigResult> = configRepository.getConfig()
}