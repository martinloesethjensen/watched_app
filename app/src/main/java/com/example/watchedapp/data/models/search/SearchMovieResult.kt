package com.example.watchedapp.data.models.search

import com.example.watchedapp.database.models.WatchlistEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchMovieResults(
    val page: Int = 0,
    val results: List<SearchMovieResult> = listOf(),
    @SerialName("total_pages")
    val totalPages: Int = 0,
    @SerialName("total_results")
    val totalResults: Int = 0,
)

@Serializable
data class SearchMovieResult(
    val id: Int,
    val adult: Boolean = false,
    val title: String,
    @SerialName("backdrop_path")
    val backdropPath: String?,
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("original_title")
    val originalTitle: String,
    val overview: String,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("genre_ids")
    val genreIds: List<Int>,
    val popularity: Double = 0.0,
    @SerialName("release_date")
    val releaseDate: String,
    val video: Boolean = false,
    @SerialName("vote_average")
    val voteAverage: Double = 0.0,
    @SerialName("vote_count")
    val voteCount: Int = 0,
)

fun SearchMovieResult.asEntity() = WatchlistEntity(
    id,
    adult,
    title,
    backdropPath,
    originalLanguage,
    originalTitle,
    overview,
    posterPath,
    genreIds,
    popularity,
    releaseDate,
    video,
    voteAverage,
    voteCount
)
