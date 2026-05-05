package dev.martinloeseth.watchedapp.data.repositories.config

import dev.martinloeseth.watchedapp.domain.models.ImageConfig
import dev.martinloeseth.watchedapp.network.ConfigNetworkDataSource
import dev.martinloeseth.watchedapp.network.models.asImageConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface ConfigRepository {
    fun getConfig(): Flow<ImageConfig>
}

class RemoteConfigRepository @Inject constructor(
    private val network: ConfigNetworkDataSource
) : ConfigRepository {
    override fun getConfig() = flow { emit(network.getConfig().asImageConfig()) }
}
