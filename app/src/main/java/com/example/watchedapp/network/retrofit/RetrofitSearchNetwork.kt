package com.example.watchedapp.network.retrofit

import com.example.watchedapp.Secrets
import com.example.watchedapp.data.models.search.SearchMovieResults
import com.example.watchedapp.data.repositories.search.SearchQuery
import com.example.watchedapp.network.SearchNetworkDataSource
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

private interface RetrofitSearchNetworkApi {
    @GET(value = "search/movie")
    suspend fun movieSearch(
        @Query("api_key") apiKey: String = Secrets.apiKey,
        @Query("query") query: String,
        @Query("page") page: Int = 1,
    ): SearchMovieResults
}

private const val ApiBaseUrl = "https://api.themoviedb.org/3/"

class RetrofitSearchNetwork @Inject constructor(
    networkJson: Json,
    okHttpCallFactory: Call.Factory,
) : SearchNetworkDataSource {
    private val networkApi = Retrofit.Builder()
        .baseUrl(ApiBaseUrl)
        .callFactory(okHttpCallFactory)
        .addConverterFactory(
            @OptIn(ExperimentalSerializationApi::class)
            networkJson.asConverterFactory("application/json".toMediaType())
        )
        .build()
        .create(RetrofitSearchNetworkApi::class.java)

    override suspend fun movieSearch(query: SearchQuery): SearchMovieResults {
        return networkApi.movieSearch(query = query.query, page = query.page)
    }
}