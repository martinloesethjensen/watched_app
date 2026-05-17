package dev.martinloeseth.watchedapp.presentation.moviedetails

import dev.martinloeseth.watchedapp.domain.models.MovieDetails
import dev.martinloeseth.watchedapp.domain.models.WatchEntry

sealed interface MovieDetailsUiState {
    object Loading : MovieDetailsUiState
    object Failure : MovieDetailsUiState
    data class Success(
        val movieDetails: MovieDetails,
        val isInWatchlist: Boolean,
        val watchHistory: List<WatchEntry>,
        val userRating: Int?,
    ) : MovieDetailsUiState {
        val isWatched: Boolean get() = watchHistory.isNotEmpty()
    }
}
