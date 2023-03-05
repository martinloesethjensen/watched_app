package com.example.watchedapp.presentation.search

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.watchedapp.R

@Composable
fun SearchRoute(
    onBackClick: () -> Unit, searchViewModel: SearchViewModel = viewModel()
) {
    SearchScreen(
        onBackClick = onBackClick,
        onClearClick = searchViewModel::clearSearch,
        onSearch = searchViewModel::search,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onBackClick: () -> Unit,
    onClearClick: () -> Unit,
    onSearch: (TextFieldValue) -> Unit,
) {
    var query by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = {
            OutlinedTextField(
                value = query,
                onValueChange = {
                    query = it
                },
                placeholder = { Text(text = stringResource(R.string.searchFieldPlaceholder)) },
                singleLine = true,
                maxLines = 1,
                trailingIcon = {
                    if (query.text.isNotBlank()) IconButton(onClick = {
                        query = TextFieldValue()
                        onClearClick()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = stringResource(R.string.clearSearchFieldContentDescription),
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { onSearch(query) }),
            )
        }, actions = {
            if (query.text.isNotBlank()) IconButton(onClick = { onSearch(query) }) {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = stringResource(R.string.completeSearchContentDescription),
                )
            }
        }, navigationIcon = {
            IconButton(onBackClick) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.backButtonContentDescription),
                )
            }
        })
    }, content = {
        Text(text = "", modifier = Modifier.padding(it))
    })
}