package com.example.watchedapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.watchedapp.domain.usecases.GetConfigUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getConfigUseCase: GetConfigUseCase
) : ViewModel() {

    val configUiState: StateFlow<ConfigUiState> =
        getConfigUseCase()
            .map {
                ConfigUiState.Success(config = it)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(3_000),
                initialValue = ConfigUiState.Loading,
            )
}