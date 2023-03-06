package com.example.watchedapp.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.watchedapp.data.models.search.SearchMovieResult

@Entity(tableName = "watchlist")
data class WatchlistEntity(
    @PrimaryKey val id: Int,
    val adult: Boolean = false,
    val title: String,
    @ColumnInfo(name = "backdrop_path") val backdropPath: String?,
    @ColumnInfo(name = "original_language") val originalLanguage: String,
    @ColumnInfo(name = "original_title") val originalTitle: String,
    val overview: String,
    @ColumnInfo(name = "poster_path") val posterPath: String?,
    @ColumnInfo(name = "genre_ids") val genreIds: List<Int>,
    val popularity: Double = 0.0,
    @ColumnInfo(name = "release_date") val releaseDate: String,
    val video: Boolean = false,
    @ColumnInfo(name = "vote_average") val voteAverage: Double = 0.0,
    @ColumnInfo(name = "vote_count") val voteCount: Int = 0,
)

fun WatchlistEntity.asExternalModel() = SearchMovieResult(
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
