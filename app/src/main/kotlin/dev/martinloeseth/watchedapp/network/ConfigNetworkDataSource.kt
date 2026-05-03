package dev.martinloeseth.watchedapp.network

import dev.martinloeseth.watchedapp.data.models.config.ConfigResult

interface ConfigNetworkDataSource {
    suspend fun getConfig(): ConfigResult
}