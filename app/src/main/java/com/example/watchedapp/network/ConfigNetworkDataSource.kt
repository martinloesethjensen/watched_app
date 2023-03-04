package com.example.watchedapp.network

import com.example.watchedapp.data.models.config.ConfigResult

interface ConfigNetworkDataSource {
    suspend fun getConfig(): ConfigResult
}