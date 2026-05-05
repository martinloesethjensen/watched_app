package dev.martinloeseth.watchedapp.network.models

import dev.martinloeseth.watchedapp.domain.models.Genre
import dev.martinloeseth.watchedapp.domain.models.MovieDetails
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreNetworkModel(val id: Int, val name: String)

@Serializable
data class MovieDetailsNetworkModel(
    val id: Int,
    val title: String,
    val overview: String = "",
    @SerialName("poster_path") val posterPath: String? = null,
    @SerialName("backdrop_path") val backdropPath: String? = null,
    @SerialName("release_date") val releaseDate: String = "",
    @SerialName("vote_average") val voteAverage: Double = 0.0,
    @SerialName("vote_count") val voteCount: Int = 0,
    val runtime: Int? = null,
    val tagline: String = "",
    val genres: List<GenreNetworkModel> = listOf(),
)

fun MovieDetailsNetworkModel.asMovieDetails() = MovieDetails(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath,
    backdropPath = backdropPath,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    voteCount = voteCount,
    runtime = runtime,
    tagline = tagline,
    genres = genres.map { Genre(it.id, it.name) },
)
