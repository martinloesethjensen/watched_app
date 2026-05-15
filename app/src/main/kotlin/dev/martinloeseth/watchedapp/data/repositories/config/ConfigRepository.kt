package dev.martinloeseth.watchedapp.data.repositories.config

import dev.martinloeseth.watchedapp.domain.models.ImageConfig
import dev.martinloeseth.watchedapp.domain.repositories.ConfigRepository
import dev.martinloeseth.watchedapp.network.ConfigNetworkDataSource
import dev.martinloeseth.watchedapp.network.models.asImageConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class RemoteConfigRepository @Inject constructor(
    private val network: ConfigNetworkDataSource,
) : ConfigRepository {
    @Volatile private var cache: ImageConfig? = null

    override fun getConfig(): Flow<ImageConfig> = flow {
        cache?.let { emit(it); return@flow }
        try {
            val config = network.getConfig().asImageConfig()
            cache = config
            emit(config)
        } catch (_: IOException) {
            throw IOException("No network connection")
        }
    }
}
