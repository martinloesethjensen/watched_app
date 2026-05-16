package dev.martinloeseth.watchedapp.data.repositories.config

import dev.martinloeseth.watchedapp.domain.models.ImageConfig
import dev.martinloeseth.watchedapp.domain.repositories.ConfigRepository
import dev.martinloeseth.watchedapp.network.ConfigNetworkDataSource
import dev.martinloeseth.watchedapp.network.models.asImageConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.io.IOException
import javax.inject.Inject

class RemoteConfigRepository @Inject constructor(
    private val network: ConfigNetworkDataSource,
) : ConfigRepository {
    private val mutex = Mutex()
    private var cache: ImageConfig? = null

    override fun getConfig(): Flow<ImageConfig> = flow {
        val result = mutex.withLock {
            try {
                cache ?: network.getConfig().asImageConfig().also { cache = it }
            } catch (_: IOException) {
                throw IOException("No network connection")
            }
        }
        emit(result)
    }
}
