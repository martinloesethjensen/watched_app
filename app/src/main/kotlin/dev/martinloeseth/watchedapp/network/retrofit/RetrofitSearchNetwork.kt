package dev.martinloeseth.watchedapp.network.retrofit

import androidx.compose.ui.util.trace
import dev.martinloeseth.watchedapp.data.repositories.search.SearchQuery
import dev.martinloeseth.watchedapp.network.SearchNetworkDataSource
import dev.martinloeseth.watchedapp.network.models.MovieSearchResultsNetworkModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dev.martinloeseth.watchedapp.BuildConfig
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

private interface RetrofitSearchNetworkApi {
    @GET(value = "search/movie")
    suspend fun search(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
    ): MovieSearchResultsNetworkModel
}

class RetrofitSearchNetwork @Inject constructor(
    networkJson: Json,
    okHttpCallFactory: Call.Factory,
) : SearchNetworkDataSource {
    private val networkApi = trace("RetrofitSearchNetwork") {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .callFactory(okHttpCallFactory)
            .addConverterFactory(
                networkJson.asConverterFactory("application/json".toMediaType()),
            )
            .build()
            .create(RetrofitSearchNetworkApi::class.java)
    }

    override suspend fun search(query: SearchQuery): MovieSearchResultsNetworkModel {
        return networkApi.search(query = query.query, page = query.page)
    }
}
