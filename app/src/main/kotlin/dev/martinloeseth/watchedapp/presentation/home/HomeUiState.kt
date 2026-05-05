package dev.martinloeseth.watchedapp.presentation.home

import dev.martinloeseth.watchedapp.domain.models.Movie

sealed interface HomeUiState {
    object Loading : HomeUiState
    object Failure : HomeUiState
    data class Success(
        val watchlist: List<Movie>,
    ) : HomeUiState
}
