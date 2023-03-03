package com.example.watchedapp.network.retrofit

import com.example.watchedapp.network.SearchNetworkDataSource
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Inject

private interface RetrofitSearchNetworkApi {

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
}