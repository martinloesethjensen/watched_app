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
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getConfigUseCase: GetConfigUseCase,
    private val getWatchlistUseCase: GetWatchlistUseCase,
    private val removeFromWatchlistUseCase: RemoveFromWatchlistUseCase,
) : ViewModel() {

    val uiState: StateFlow<HomeUiState> =
        combine(
            getWatchlistUseCase(),
            getConfigUseCase(),
            ::Pair,
        )
            .asResult()
            .map {
                when (it) {
                    is Result.Error -> HomeUiState.Failure
                    Result.Loading -> HomeUiState.Loading
                    is Result.Success -> {
                        val (watchlist, config) = it.data
                        HomeUiState.Success(watchlist, config)
                    }
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = HomeUiState.Loading
            )

    fun removeFromWatchlist(movie: SearchMovieResult) {
        viewModelScope.launch {
            removeFromWatchlistUseCase(movie.id)
        }
    }
}