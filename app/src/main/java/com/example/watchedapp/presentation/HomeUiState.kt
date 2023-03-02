package com.example.watchedapp.presentation

sealed interface HomeUiState {
    // TODO: change watched type
    data class Success(val watched: String) : HomeUiState
    object Error : HomeUiState
    object Loading : HomeUiState
}
