package com.example.watchedapp.presentation.home

import com.example.watchedapp.data.models.search.SearchMovieResult

sealed interface HomeUiState {
    object Loading : HomeUiState
    object Failure : HomeUiState
    data class Success(val watchlist: List<SearchMovieResult>) : HomeUiState
}