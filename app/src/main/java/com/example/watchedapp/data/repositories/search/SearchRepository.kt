package com.example.watchedapp.data.repositories.search

import com.example.watchedapp.data.models.search.SearchMovieResults
import com.example.watchedapp.network.SearchNetworkDataSource
import javax.inject.Inject

data class SearchQuery(
    val query: String,
    val page: Int = 1,
)

interface SearchRepository {
    suspend fun movieSearch(query: SearchQuery): SearchMovieResults
}

class RemoteSearchRepository @Inject constructor(
    private val network: SearchNetworkDataSource
) : SearchRepository {
    override suspend fun movieSearch(query: SearchQuery): SearchMovieResults {
        return network.movieSearch(query)
    }
}