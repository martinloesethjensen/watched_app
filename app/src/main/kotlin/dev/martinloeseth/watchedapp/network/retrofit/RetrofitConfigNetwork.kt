package dev.martinloeseth.watchedapp.network.retrofit

import dev.martinloeseth.watchedapp.data.models.config.ConfigResult
import dev.martinloeseth.watchedapp.network.ConfigNetworkDataSource
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dev.martinloeseth.watchedapp.BuildConfig
import kotlinx.serialization.ExperimentalSerializationApi
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
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): ConfigResult
}

class RetrofitConfigNetwork @Inject constructor(
    networkJson: Json,
    okHttpCallFactory: Call.Factory,
) : ConfigNetworkDataSource {
    private val networkApi = Retrofit.Builder()
        .baseUrl(BuildConfig.API_BASE_URL)
        .callFactory(okHttpCallFactory)
        .addConverterFactory(
            @OptIn(ExperimentalSerializationApi::class)
            networkJson.asConverterFactory("application/json".toMediaType())
        )
        .build()
        .create(RetrofitConfigNetworkApi::class.java)

    override suspend fun getConfig(): ConfigResult = networkApi.getConfig()
}