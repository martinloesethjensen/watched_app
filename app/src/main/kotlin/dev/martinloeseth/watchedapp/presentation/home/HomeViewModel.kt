package dev.martinloeseth.watchedapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.martinloeseth.watchedapp.domain.models.Movie
import dev.martinloeseth.watchedapp.domain.core.result.Result
import dev.martinloeseth.watchedapp.domain.core.result.asResult
import dev.martinloeseth.watchedapp.domain.usecases.GetConfigUseCase
import dev.martinloeseth.watchedapp.domain.usecases.GetWatchlistUseCase
import dev.martinloeseth.watchedapp.domain.usecases.RemoveFromWatchlistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getConfigUseCase: GetConfigUseCase,
    getWatchlistUseCase: GetWatchlistUseCase,
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
                        val (watchlist, _) = it.data
                        HomeUiState.Success(watchlist)
                    }
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = HomeUiState.Loading
            )

    fun removeFromWatchlist(movie: Movie) {
        viewModelScope.launch {
            removeFromWatchlistUseCase(movie.id)
        }
    }
}
