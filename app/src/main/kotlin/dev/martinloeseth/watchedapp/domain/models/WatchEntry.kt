package dev.martinloeseth.watchedapp.domain.models

data class WatchEntry(
    val id: Int,
    val movieId: Int,
    val watchedAt: Long,
)