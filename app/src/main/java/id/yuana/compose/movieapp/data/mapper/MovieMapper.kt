package id.yuana.compose.movieapp.data.mapper

import android.annotation.SuppressLint
import id.yuana.compose.movieapp.data.local.database.entity.MovieEntity
import id.yuana.compose.movieapp.data.remote.response.GetMovieDetailResponse
import id.yuana.compose.movieapp.data.remote.response.GetMoviePopularResponse
import id.yuana.compose.movieapp.domain.model.Movie
import java.text.SimpleDateFormat
import java.util.*

fun GetMoviePopularResponse.Result.toModel(): Movie {
    return Movie(
        id = id,
        title = title,
        titleOriginal = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        backdropPath = backdropPath ?: "",
        voteAverage = voteAverage,
        voteCount = voteCount,
        releaseDate = releaseDate,
        genres = listOf(),
        runtime = 0,
        tagline = ""
    )
}

@SuppressLint("SimpleDateFormat")
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
        releaseDate = SimpleDateFormat("yyyy-MM-dd").parse(releaseDate) ?: Date(),
        genres = genres.map { it.toModel() },
        runtime = runtime,
        tagline = tagline
    )
}

fun Movie.toEntity(): MovieEntity {
    return MovieEntity(
        id = id,
        title = title,
        titleOriginal = titleOriginal,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        backdropPath = backdropPath,
        voteAverage = voteAverage,
        voteCount = voteCount,
        releaseDate = releaseDate,
        runtime = runtime,
        tagline = tagline
    )
}

fun MovieEntity.toModel(): Movie {
    return Movie(
        id = id,
        title = title,
        titleOriginal = titleOriginal,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        backdropPath = backdropPath,
        voteAverage = voteAverage,
        voteCount = voteCount,
        releaseDate = releaseDate,
        runtime = runtime,
        tagline = tagline
    )
}