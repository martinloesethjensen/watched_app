package dev.martinloeseth.watchedapp.data.repositories.search

import dev.martinloeseth.watchedapp.domain.models.MovieSearchResults
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.mapLatest

class TestSearchRepository : SearchRepository {
    private val searchResourceFlow: MutableSharedFlow<MovieSearchResults> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun search(query: SearchQuery): Flow<MovieSearchResults> =
        searchResourceFlow.mapLatest {
            it.copy(results = it.results.filter { movie ->
                movie.title.contains(query.query, ignoreCase = true)
            })
        }

    fun sendSearchResources(result: MovieSearchResults) {
        searchResourceFlow.tryEmit(result)
    }
}
