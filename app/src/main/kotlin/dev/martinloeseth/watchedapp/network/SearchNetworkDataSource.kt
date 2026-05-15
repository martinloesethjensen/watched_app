package dev.martinloeseth.watchedapp.network

import dev.martinloeseth.watchedapp.domain.models.SearchQuery
import dev.martinloeseth.watchedapp.network.models.MovieSearchResultsNetworkModel

interface SearchNetworkDataSource {
    suspend fun search(query: SearchQuery): MovieSearchResultsNetworkModel
}
