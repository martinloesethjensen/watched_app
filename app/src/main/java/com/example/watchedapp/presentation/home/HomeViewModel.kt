package com.example.watchedapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.watchedapp.domain.usecases.GetConfigUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getConfigUseCase: GetConfigUseCase
) : ViewModel() {
    init {
        // TODO: get realm watchlist
    }


    val configUiState: StateFlow<ConfigUiState> =
        getConfigUseCase()
            .map {
                ConfigUiState.Success(config = it)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(3_000),
                initialValue = ConfigUiState.Loading,
            )

    // handle search and calls to use cases
    // injection with hilt
}