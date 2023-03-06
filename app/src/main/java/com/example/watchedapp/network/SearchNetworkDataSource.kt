package com.example.watchedapp.network

import com.example.watchedapp.data.models.search.SearchMovieResults
import com.example.watchedapp.data.repositories.search.SearchQuery

interface SearchNetworkDataSource {

    suspend fun movieSearch(query: SearchQuery): SearchMovieResults
}
