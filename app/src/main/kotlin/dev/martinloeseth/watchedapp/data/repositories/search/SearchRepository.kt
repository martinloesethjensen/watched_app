package dev.martinloeseth.watchedapp.data.repositories.search

import dev.martinloeseth.watchedapp.data.models.search.SearchMovieResults
import dev.martinloeseth.watchedapp.network.SearchNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

data class SearchQuery(
    val query: String,
    val page: Int = 1,
)

interface SearchRepository {
    fun search(query: SearchQuery): Flow<SearchMovieResults>
}

class RemoteSearchRepository @Inject constructor(
    private val network: SearchNetworkDataSource
) : SearchRepository {
    override fun search(query: SearchQuery) = flow {
        emit(network.search(query))
    }
}