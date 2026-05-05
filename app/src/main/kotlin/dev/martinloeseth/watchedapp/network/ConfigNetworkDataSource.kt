package dev.martinloeseth.watchedapp.network

import dev.martinloeseth.watchedapp.network.models.ConfigNetworkModel

interface ConfigNetworkDataSource {
    suspend fun getConfig(): ConfigNetworkModel
}
