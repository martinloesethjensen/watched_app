package com.example.watchedapp.network.retrofit

import com.example.watchedapp.Secrets
import com.example.watchedapp.data.models.config.ConfigResult
import com.example.watchedapp.network.ConfigNetworkDataSource
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

import javax.inject.Inject

private interface RetrofitConfigNetworkApi {
    @GET(value = "configuration")
    suspend fun getConfig(
        @Query("api_key") apiKey: String = Secrets.apiKey,
    ): ConfigResult
}

private const val ApiBaseUrl = "https://api.themoviedb.org/3/"

class RetrofitConfigNetwork @Inject constructor(
    networkJson: Json,
    okHttpCallFactory: Call.Factory,
) : ConfigNetworkDataSource {
    private val networkApi = Retrofit.Builder()
        .baseUrl(ApiBaseUrl)
        .callFactory(okHttpCallFactory)
        .addConverterFactory(
            @OptIn(ExperimentalSerializationApi::class)
            networkJson.asConverterFactory("application/json".toMediaType())
        )
        .build()
        .create(RetrofitConfigNetworkApi::class.java)

    override suspend fun getConfig(): ConfigResult = networkApi.getConfig()
}