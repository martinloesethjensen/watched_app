package com.example.watchedapp.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.watchedapp.data.models.search.SearchMovieResult
import com.example.watchedapp.data.repositories.search.SearchQuery
import com.example.watchedapp.domain.core.result.Result
import com.example.watchedapp.domain.core.result.asResult
import com.example.watchedapp.domain.usecases.AddToWatchlistUseCase
import com.example.watchedapp.domain.usecases.GetSearchResultsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchResultsUseCase: GetSearchResultsUseCase,
    private val addToWatchlistUseCase: AddToWatchlistUseCase,
) : ViewModel() {

    private val _searchUiState = MutableStateFlow<SearchUiState>(SearchUiState.Initial)
    val searchUiState: StateFlow<SearchUiState> = _searchUiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3_000),
        initialValue = SearchUiState.Initial,
    )

    private val _queryState = MutableStateFlow("")
    val queryState: StateFlow<String> = _queryState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3_000),
        initialValue = "",
    )

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    fun search(query: String) {
        /// early return if user tries to search for the same query
        if (_queryState.value == query) return

        _queryState.value = query

        val queryStateValue = _queryState.value

        if (queryStateValue.isBlank() or (queryStateValue.length < 2)) return

        viewModelScope.launch {
            _queryState
                .debounce(300.milliseconds)
                .distinctUntilChanged()
                .flatMapLatest { q ->
                    getSearchResultsUseCase(SearchQuery(query = q.trim()))
                }.asResult().collect { result ->
                    _searchUiState.value = when (result) {
                        is Result.Error -> SearchUiState.Failure
                        Result.Loading -> SearchUiState.Loading
                        is Result.Success -> SearchUiState.Success(result.data)
                    }
                }
        }
    }

    fun addToWatchlist(item: SearchMovieResult) {
        viewModelScope.launch {
            addToWatchlistUseCase(item)
        }
    }

    /// Remove any results from ui state
    fun clearSearch() {
        _queryState.value = ""
        _searchUiState.value = SearchUiState.Initial
    }
}