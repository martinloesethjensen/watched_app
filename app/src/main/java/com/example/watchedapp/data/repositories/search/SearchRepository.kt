package com.example.watchedapp.data.repositories.search

import com.example.watchedapp.data.models.search.SearchMovieResults
import com.example.watchedapp.network.SearchNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

data class SearchQuery(
    val query: String,
    val page: Int = 1,
)

interface SearchRepository {
    fun movieSearch(query: SearchQuery): Flow<SearchMovieResults>
}

class RemoteSearchRepository @Inject constructor(
    private val network: SearchNetworkDataSource
) : SearchRepository {
    override fun movieSearch(query: SearchQuery) = flow {
        emit(network.movieSearch(query))
    }
}