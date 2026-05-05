package dev.martinloeseth.watchedapp.network

import dev.martinloeseth.watchedapp.data.repositories.search.SearchQuery
import dev.martinloeseth.watchedapp.network.models.MovieSearchResultsNetworkModel

interface SearchNetworkDataSource {
    suspend fun search(query: SearchQuery): MovieSearchResultsNetworkModel
}
