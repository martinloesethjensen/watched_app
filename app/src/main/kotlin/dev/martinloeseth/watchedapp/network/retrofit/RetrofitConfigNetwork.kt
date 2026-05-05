package dev.martinloeseth.watchedapp.network.retrofit

import androidx.compose.ui.util.trace
import dev.martinloeseth.watchedapp.network.ConfigNetworkDataSource
import dev.martinloeseth.watchedapp.network.models.ConfigNetworkModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dev.martinloeseth.watchedapp.BuildConfig
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import javax.inject.Inject

private interface RetrofitConfigNetworkApi {
    @GET(value = "configuration")
    suspend fun getConfig(): ConfigNetworkModel
}

class RetrofitConfigNetwork @Inject constructor(
    networkJson: Json,
    okHttpCallFactory: Call.Factory,
) : ConfigNetworkDataSource {
    private val networkApi = trace("RetrofitConfigNetwork") {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .callFactory(okHttpCallFactory)
            .addConverterFactory(
                networkJson.asConverterFactory("application/json".toMediaType())
            )
            .build()
            .create(RetrofitConfigNetworkApi::class.java)
    }

    override suspend fun getConfig(): ConfigNetworkModel = networkApi.getConfig()
}
