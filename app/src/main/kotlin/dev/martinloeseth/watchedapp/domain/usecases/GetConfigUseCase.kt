package dev.martinloeseth.watchedapp.domain.usecases

import dev.martinloeseth.watchedapp.domain.repositories.ConfigRepository
import dev.martinloeseth.watchedapp.domain.models.ImageConfig
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetConfigUseCase @Inject constructor(
    private val configRepository: ConfigRepository,
) {
    operator fun invoke(): Flow<ImageConfig> = configRepository.getConfig()
}
