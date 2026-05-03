package dev.martinloeseth.watchedapp.data.repositories.config

import dev.martinloeseth.watchedapp.data.models.config.ConfigResult
import dev.martinloeseth.watchedapp.network.ConfigNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface ConfigRepository {
    fun getConfig(): Flow<ConfigResult>
}

class RemoteConfigRepository @Inject constructor(
    private val network: ConfigNetworkDataSource
) : ConfigRepository {
    override fun getConfig() = flow { emit(network.getConfig()) }
}