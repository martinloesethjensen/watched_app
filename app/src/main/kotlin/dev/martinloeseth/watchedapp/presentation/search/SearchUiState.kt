package dev.martinloeseth.watchedapp.presentation.search

import dev.martinloeseth.watchedapp.domain.models.MovieSearchResults

sealed interface SearchUiState {
    object Initial : SearchUiState
    object Loading : SearchUiState
    object Failure : SearchUiState
    data class Success(val searchResults: MovieSearchResults) : SearchUiState
}
