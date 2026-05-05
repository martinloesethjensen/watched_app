package dev.martinloeseth.watchedapp.presentation.moviedetails

import dev.martinloeseth.watchedapp.domain.models.MovieDetails

sealed interface MovieDetailsUiState {
    object Loading : MovieDetailsUiState
    object Failure : MovieDetailsUiState
    data class Success(
        val movieDetails: MovieDetails,
        val isInWatchlist: Boolean,
    ) : MovieDetailsUiState
}
