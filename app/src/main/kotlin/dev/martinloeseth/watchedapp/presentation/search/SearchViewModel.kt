package dev.martinloeseth.watchedapp.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.martinloeseth.watchedapp.domain.models.SearchQuery
import dev.martinloeseth.watchedapp.domain.core.result.Result
import dev.martinloeseth.watchedapp.domain.core.result.asResult
import dev.martinloeseth.watchedapp.domain.usecases.GetSearchResultsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchResultsUseCase: GetSearchResultsUseCase,
) : ViewModel() {

    private val _queryState = MutableStateFlow("")
    val queryState: StateFlow<String> = _queryState.asStateFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val searchUiState: StateFlow<SearchUiState> = _queryState
        .debounce(750.milliseconds)
        .flatMapLatest { query ->
            if (query.isBlank() || query.length < 2) {
                flowOf(SearchUiState.Initial)
            } else {
                getSearchResultsUseCase(SearchQuery(query.trim()))
                    .asResult()
                    .map { result ->
                        when (result) {
                            is Result.Error -> SearchUiState.Failure
                            Result.Loading -> SearchUiState.Loading
                            is Result.Success -> SearchUiState.Success(result.data)
                        }
                    }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SearchUiState.Initial,
        )

    fun search(query: String) {
        _queryState.update { query }
    }

    fun clearSearch() {
        _queryState.update { "" }
    }
}
