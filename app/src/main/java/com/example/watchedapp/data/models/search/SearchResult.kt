package com.example.watchedapp.data.models.search

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResults(
    val page: Int,
    val results: ArrayList<SearchResult> = arrayListOf(),
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int,
)

@Serializable
data class SearchResult(
    val id: Int,
    val adult: Boolean = false,
)
