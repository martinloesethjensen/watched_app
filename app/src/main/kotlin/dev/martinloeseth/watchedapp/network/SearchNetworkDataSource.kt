package dev.martinloeseth.watchedapp.network

import dev.martinloeseth.watchedapp.data.models.search.SearchMovieResults
import dev.martinloeseth.watchedapp.data.repositories.search.SearchQuery

interface SearchNetworkDataSource {

    suspend fun movieSearch(query: SearchQuery): SearchMovieResults
}
