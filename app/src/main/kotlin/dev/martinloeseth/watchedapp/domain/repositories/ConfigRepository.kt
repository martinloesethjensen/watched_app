package dev.martinloeseth.watchedapp.domain.repositories

import dev.martinloeseth.watchedapp.domain.models.ImageConfig
import kotlinx.coroutines.flow.Flow

interface ConfigRepository {
    fun getConfig(): Flow<ImageConfig>
}
