package com.example.watchedapp.presentation.search

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {
    var query = mutableStateOf(TextFieldValue())

    fun search(query: TextFieldValue) {
        if (query.text.isNotBlank() and (query.text.length < 2)) return

        val trimmedQuery = query.text.trim()



        Log.d("search", trimmedQuery)
    }

    /// Remove any results from ui state
    fun clearSearch() {
        Log.d("clearSearch", "do something")
    }
}