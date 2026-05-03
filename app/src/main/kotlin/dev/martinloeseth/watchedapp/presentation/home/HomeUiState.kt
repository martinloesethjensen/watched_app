package dev.martinloeseth.watchedapp.presentation.home

import dev.martinloeseth.watchedapp.data.models.config.ConfigResult
import dev.martinloeseth.watchedapp.data.models.search.SearchMovieResult

sealed interface HomeUiState {
    object Loading : HomeUiState
    object Failure : HomeUiState
    data class Success(
        val watchlist: List<SearchMovieResult>,
    ) : HomeUiState
}