package dev.martinloeseth.watchedapp.domain.usecases

import dev.martinloeseth.watchedapp.data.models.search.SearchMovieResults
import dev.martinloeseth.watchedapp.data.repositories.search.SearchQuery
import dev.martinloeseth.watchedapp.data.repositories.search.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchResultsUseCase @Inject constructor(
    private val searchRepository: SearchRepository,
) {
    operator fun invoke(query: SearchQuery): Flow<SearchMovieResults> =
        searchRepository.search(query)
}