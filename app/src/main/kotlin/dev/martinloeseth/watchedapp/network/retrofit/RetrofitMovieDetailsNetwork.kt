package dev.martinloeseth.watchedapp.network.retrofit

import androidx.compose.ui.util.trace
import dev.martinloeseth.watchedapp.BuildConfig
import dev.martinloeseth.watchedapp.network.MovieDetailsNetworkDataSource
import dev.martinloeseth.watchedapp.network.models.MovieDetailsNetworkModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Inject

private interface RetrofitMovieDetailsNetworkApi {
    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId: Int): MovieDetailsNetworkModel
}

class RetrofitMovieDetailsNetwork @Inject constructor(
    networkJson: Json,
    okHttpCallFactory: Call.Factory,
) : MovieDetailsNetworkDataSource {
    private val networkApi = trace("RetrofitMovieDetailsNetwork") {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .callFactory(okHttpCallFactory)
            .addConverterFactory(
                networkJson.asConverterFactory("application/json".toMediaType())
            )
            .build()
            .create(RetrofitMovieDetailsNetworkApi::class.java)
    }

    override suspend fun getMovieDetails(movieId: Int): MovieDetailsNetworkModel =
        networkApi.getMovieDetails(movieId)
}
