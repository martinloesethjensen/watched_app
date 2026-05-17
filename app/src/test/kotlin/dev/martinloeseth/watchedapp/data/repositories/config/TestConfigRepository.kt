package dev.martinloeseth.watchedapp.data.repositories.config

import dev.martinloeseth.watchedapp.domain.models.ImageConfig
import dev.martinloeseth.watchedapp.domain.repositories.ConfigRepository
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class TestConfigRepository : ConfigRepository {
    private val configResourceFlow: MutableSharedFlow<ImageConfig> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override fun getConfig(): Flow<ImageConfig> = configResourceFlow

    fun sendConfigResource(config: ImageConfig) {
        configResourceFlow.tryEmit(config)
    }
}
