package com.example.watchedapp.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = ""

private val retrofit =
    Retrofit.Builder().addConverterFactory(ScalarsConverterFactory.create()).baseUrl(BASE_URL)
        .build()

interface WatchedApiService {}