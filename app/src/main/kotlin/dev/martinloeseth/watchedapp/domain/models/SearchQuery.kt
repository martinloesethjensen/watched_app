package dev.martinloeseth.watchedapp.domain.models

data class SearchQuery(
    val query: String,
    val page: Int = 1,
)
