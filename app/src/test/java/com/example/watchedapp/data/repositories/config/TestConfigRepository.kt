package com.example.watchedapp.data.repositories.config

import com.example.watchedapp.data.models.config.ConfigResult
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class TestConfigRepository : ConfigRepository {
    private val configResourceFlow: MutableSharedFlow<ConfigResult> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override fun getConfig(): Flow<ConfigResult> = configResourceFlow

    fun sendConfigResource(config: ConfigResult) {
        configResourceFlow.tryEmit(config)
    }
}