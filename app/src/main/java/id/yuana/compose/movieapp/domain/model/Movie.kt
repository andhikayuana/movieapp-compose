package id.yuana.compose.movieapp.domain.model

import java.util.Date

data class Movie(
    val id: Int,
    val title: String,
    val titleOriginal: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val backdropPath: String,
    val voteAverage: Double,
    val voteCount: Int,
    val releaseDate: Date,
    val genres: List<Genre>,
    val runtime: Int,
    val tagline: String
)
