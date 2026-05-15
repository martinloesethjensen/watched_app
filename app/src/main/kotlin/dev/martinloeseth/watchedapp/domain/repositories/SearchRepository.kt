package dev.martinloeseth.watchedapp.domain.repositories

import dev.martinloeseth.watchedapp.domain.models.MovieSearchResults
import dev.martinloeseth.watchedapp.domain.models.SearchQuery
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun search(query: SearchQuery): Flow<MovieSearchResults>
}
