package id.yuana.compose.movieapp.data.mapper

import id.yuana.compose.movieapp.data.remote.response.GetMovieDetailResponse
import id.yuana.compose.movieapp.data.remote.response.GetMoviePopularResponse
import id.yuana.compose.movieapp.domain.model.Movie
import java.util.*

fun GetMoviePopularResponse.Result.toModel(): Movie {
    return Movie(
        id = id,
        title = title,
        titleOriginal = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        backdropPath = backdropPath,
        voteAverage = voteAverage,
        voteCount = voteCount,
        releaseDate = releaseDate,
        genres = listOf(),
        runtime = 0,
        tagline = ""
    )
}

fun GetMovieDetailResponse.toModel(): Movie {
    return Movie(
        id = id,
        title = title,
        titleOriginal = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        backdropPath = backdropPath,
        voteAverage = voteAverage,
        voteCount = voteCount,
        releaseDate = Date(),
        genres = genres.map { it.toModel() },
        runtime = runtime,
        tagline = tagline
    )
}