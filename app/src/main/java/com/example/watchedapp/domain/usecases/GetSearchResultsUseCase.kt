package com.example.watchedapp.domain.usecases

import com.example.watchedapp.data.models.search.SearchMovieResults
import com.example.watchedapp.data.repositories.search.SearchQuery
import com.example.watchedapp.data.repositories.search.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchResultsUseCase @Inject constructor(
    private val searchRepository: SearchRepository,
) {
    operator fun invoke(query: SearchQuery): Flow<SearchMovieResults> =
        searchRepository.movieSearch(query)
}