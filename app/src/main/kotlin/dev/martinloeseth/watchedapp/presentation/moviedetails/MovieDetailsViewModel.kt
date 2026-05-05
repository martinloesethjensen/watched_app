package dev.martinloeseth.watchedapp.presentation.moviedetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.martinloeseth.watchedapp.domain.core.result.Result
import dev.martinloeseth.watchedapp.domain.core.result.asResult
import dev.martinloeseth.watchedapp.domain.usecases.GetMovieDetailsUseCase
import dev.martinloeseth.watchedapp.domain.usecases.GetWatchlistUseCase
import dev.martinloeseth.watchedapp.navigation.Details
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getMovieDetailsUseCase: GetMovieDetailsUseCase,
    getWatchlistUseCase: GetWatchlistUseCase,
) : ViewModel() {

    private val movieId: Int = savedStateHandle.toRoute<Details>().movieId

    val uiState: StateFlow<MovieDetailsUiState> = combine(
        getMovieDetailsUseCase(movieId).asResult(),
        getWatchlistUseCase(),
    ) { detailsResult, watchlist ->
        when (detailsResult) {
            is Result.Error -> MovieDetailsUiState.Failure
            Result.Loading -> MovieDetailsUiState.Loading
            is Result.Success -> MovieDetailsUiState.Success(
                movieDetails = detailsResult.data,
                isInWatchlist = watchlist.any { it.id == movieId },
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MovieDetailsUiState.Loading,
    )
}
