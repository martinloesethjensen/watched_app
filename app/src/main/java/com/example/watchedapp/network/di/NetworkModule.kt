package com.example.watchedapp.network.di

import com.example.watchedapp.network.ConfigNetworkDataSource
import com.example.watchedapp.network.retrofit.RetrofitConfigNetwork
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun okHttpCallFactory(): Call.Factory = OkHttpClient.Builder().build()

    @Provides
    @Singleton
    fun providesRetrofitConfigNetwork(): ConfigNetworkDataSource =
        RetrofitConfigNetwork(
            networkJson = providesNetworkJson(),
            okHttpCallFactory = okHttpCallFactory()
        )
}