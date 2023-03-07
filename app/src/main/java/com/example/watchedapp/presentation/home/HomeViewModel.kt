package com.example.watchedapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.watchedapp.data.models.search.SearchMovieResult
import com.example.watchedapp.domain.core.result.Result
import com.example.watchedapp.domain.core.result.asResult
import com.example.watchedapp.domain.usecases.GetConfigUseCase
import com.example.watchedapp.domain.usecases.GetWatchlistUseCase
import com.example.watchedapp.domain.usecases.RemoveFromWatchlistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getConfigUseCase: GetConfigUseCase,
    private val getWatchlistUseCase: GetWatchlistUseCase,
    private val removeFromWatchlistUseCase: RemoveFromWatchlistUseCase,
) : ViewModel() {

    val homeUiState: StateFlow<HomeUiState> =
        getWatchlistUseCase()
            .asResult()
            .map {
                when (it) {
                    is Result.Error -> HomeUiState.Failure
                    Result.Loading -> HomeUiState.Loading
                    is Result.Success -> HomeUiState.Success(it.data)
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(3_000),
                initialValue = HomeUiState.Loading
            )

    val configUiState: StateFlow<ConfigUiState> =
        getConfigUseCase()
            .asResult()
            .map {
                when (it) {
                    is Result.Error -> ConfigUiState.Failure
                    Result.Loading -> ConfigUiState.Loading
                    is Result.Success -> ConfigUiState.Success(it.data)
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(3_000),
                initialValue = ConfigUiState.Loading,
            )

    fun removeFromWatchlist(movie: SearchMovieResult) {
        viewModelScope.launch {
            removeFromWatchlistUseCase(movie.id)
        }
    }
}