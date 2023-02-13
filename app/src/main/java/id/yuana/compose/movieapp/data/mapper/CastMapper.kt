package id.yuana.compose.movieapp.data.mapper

import id.yuana.compose.movieapp.data.remote.response.GetMovieCreditsResponse
import id.yuana.compose.movieapp.domain.model.Cast

fun GetMovieCreditsResponse.Cast.toModel(): Cast {
    return Cast(
        adult,
        gender,
        id,
        knownForDepartment,
        name,
        originalName,
        popularity,
        profilePath,
        castId,
        character,
        creditId,
        order
    )
}