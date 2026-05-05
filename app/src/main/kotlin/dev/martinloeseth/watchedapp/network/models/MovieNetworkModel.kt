package dev.martinloeseth.watchedapp.network.models

import dev.martinloeseth.watchedapp.domain.models.Movie
import dev.martinloeseth.watchedapp.domain.models.MovieSearchResults
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieSearchResultsNetworkModel(
    val page: Int = 0,
    val results: List<MovieNetworkModel> = listOf(),
    @SerialName("total_pages") val totalPages: Int = 0,
    @SerialName("total_results") val totalResults: Int = 0,
)

@Serializable
data class MovieNetworkModel(
    val id: Int,
    val adult: Boolean = false,
    val title: String,
    @SerialName("backdrop_path") val backdropPath: String? = null,
    @SerialName("original_language") val originalLanguage: String,
    @SerialName("original_title") val originalTitle: String,
    val overview: String,
    @SerialName("poster_path") val posterPath: String? = null,
    @SerialName("genre_ids") val genreIds: List<Int> = listOf(),
    val popularity: Double = 0.0,
    @SerialName("release_date") val releaseDate: String,
    val video: Boolean = false,
    @SerialName("vote_average") val voteAverage: Double = 0.0,
    @SerialName("vote_count") val voteCount: Int = 0,
)

fun MovieNetworkModel.asMovie() = Movie(
    id, adult, title, backdropPath, originalLanguage, originalTitle,
    overview, posterPath, genreIds, popularity, releaseDate, video, voteAverage, voteCount,
)

fun MovieSearchResultsNetworkModel.asMovieSearchResults() = MovieSearchResults(
    page, results.map { it.asMovie() }, totalPages, totalResults,
)
