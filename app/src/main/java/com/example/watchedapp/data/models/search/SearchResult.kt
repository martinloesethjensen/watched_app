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
    val title: String,
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("original_title")
    val originalTitle: String,
    val overview: String,
    @SerialName("poster_path")
    val posterPath: String,
    @SerialName("media_type")
    val mediaType: String,
    @SerialName("genre_ids")
    val genreIds: String,
    val popularity: Double = 0.0,
    @SerialName("release_date")
    val releaseDate: String,
    val video: Boolean = false,
    @SerialName("vote_average")
    val voteAverage: Double = 0.0,
    @SerialName("vote_count")
    val voteCount: Int = 0,
)
