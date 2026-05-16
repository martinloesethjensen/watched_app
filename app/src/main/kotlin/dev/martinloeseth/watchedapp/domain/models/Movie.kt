package dev.martinloeseth.watchedapp.domain.models

import kotlinx.serialization.Serializable
import java.time.DateTimeException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.format.FormatStyle

@Serializable
data class Movie(
    val id: Int,
    val adult: Boolean = false,
    val title: String,
    val backdropPath: String? = null,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val posterPath: String? = null,
    val genreIds: List<Int> = listOf(),
    val popularity: Double = 0.0,
    val releaseDate: String,
    val video: Boolean = false,
    val voteAverage: Double = 0.0,
    val voteCount: Int = 0,
    val userRating: Int? = null,
)

data class MovieSearchResults(
    val page: Int = 0,
    val results: List<Movie> = listOf(),
    val totalPages: Int = 0,
    val totalResults: Int = 0,
)

fun String.toLocalDateOrNull(): LocalDate? {
    val date = runCatching { LocalDate.parse(this) }.getOrNull()
    return date
}

