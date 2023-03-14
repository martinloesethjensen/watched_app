package com.example.watchedapp.data.repositories.search

import com.example.watchedapp.data.models.search.SearchMovieResults
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.mapLatest

class TestSearchRepository : SearchRepository {
    private val searchResourceFlow: MutableSharedFlow<SearchMovieResults> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun movieSearch(query: SearchQuery): Flow<SearchMovieResults> =
        searchResourceFlow.mapLatest {
            it.copy(results = it.results.filter { movie ->
                movie.title.contains(
                    query.query,
                    ignoreCase = true
                )
            })
        }

    fun sendSearchResources(result: SearchMovieResults) {
        searchResourceFlow.tryEmit(result)
    }
}
