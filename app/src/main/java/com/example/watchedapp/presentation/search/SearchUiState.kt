package com.example.watchedapp.presentation.search

import com.example.watchedapp.data.models.search.SearchMovieResults


sealed interface SearchUiState {
    object Initial : SearchUiState
    object Loading : SearchUiState
    object Failure : SearchUiState
    data class Success(val searchResults: SearchMovieResults) : SearchUiState
}