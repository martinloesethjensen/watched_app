package com.example.watchedapp.presentation.search

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.watchedapp.domain.core.result.Result
import com.example.watchedapp.domain.core.result.asResult
import com.example.watchedapp.data.repositories.search.SearchQuery
import com.example.watchedapp.domain.usecases.GetSearchResultsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchResultsUseCase: GetSearchResultsUseCase
) : ViewModel() {

    private val _searchUiState = MutableStateFlow<SearchUiState>(SearchUiState.Initial)
    val searchUiState: StateFlow<SearchUiState> = _searchUiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3_000),
        initialValue = SearchUiState.Initial,
    )

    @OptIn(FlowPreview::class)
    fun search(query: TextFieldValue) {
        if (query.text.isNotBlank() and (query.text.length < 2)) return

        viewModelScope.launch {
            getSearchResultsUseCase(SearchQuery(query = query.text.trim()))
                .debounce(2_000)
                .asResult().collect { result ->
                    _searchUiState.value = when (result) {
                        is Result.Error -> SearchUiState.Failure
                        Result.Loading -> SearchUiState.Loading
                        is Result.Success -> SearchUiState.Success(result.data)
                    }
                }
        }
    }

    /// Remove any results from ui state
    fun clearSearch() {
        _searchUiState.value = SearchUiState.Initial
    }
}