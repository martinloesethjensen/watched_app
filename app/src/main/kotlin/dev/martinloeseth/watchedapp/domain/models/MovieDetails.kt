package dev.martinloeseth.watchedapp.domain.models

data class Genre(val id: Int, val name: String)

data class MovieDetails(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val runtime: Int?,
    val tagline: String,
    val genres: List<Genre>,
)
