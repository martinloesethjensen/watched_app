package com.example.watchedapp.data.repositories.config

import com.example.watchedapp.data.models.config.ConfigResult
import com.example.watchedapp.network.ConfigNetworkDataSource
import javax.inject.Inject

interface ConfigRepository {
    suspend fun getConfig(): ConfigResult
}

class RemoteConfigRepository @Inject constructor(
    private val network: ConfigNetworkDataSource
) : ConfigRepository {
    override suspend fun getConfig() =  network.getConfig()
}