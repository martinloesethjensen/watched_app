package dev.martinloeseth.watchedapp.data.repositories.search

import dev.martinloeseth.watchedapp.domain.models.MovieSearchResults
import dev.martinloeseth.watchedapp.domain.models.SearchQuery
import dev.martinloeseth.watchedapp.domain.repositories.SearchRepository
import dev.martinloeseth.watchedapp.network.SearchNetworkDataSource
import dev.martinloeseth.watchedapp.network.models.asMovieSearchResults
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class RemoteSearchRepository @Inject constructor(
    private val network: SearchNetworkDataSource,
) : SearchRepository {
    override fun search(query: SearchQuery): Flow<MovieSearchResults> = flow {
        try {
            emit(network.search(query).asMovieSearchResults())
        } catch (_: IOException) {
            throw IOException("No network connection")
        }
    }
}
