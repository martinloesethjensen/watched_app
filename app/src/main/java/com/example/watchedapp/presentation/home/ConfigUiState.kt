package com.example.watchedapp.presentation.home

import com.example.watchedapp.data.models.config.ConfigResult

sealed interface ConfigUiState {
    object Loading : ConfigUiState
    data class Success(val config: ConfigResult) : ConfigUiState
}