package id.yuana.compose.movieapp.data.mapper

import id.yuana.compose.movieapp.data.remote.response.GetMovieCreditsResponse
import id.yuana.compose.movieapp.domain.model.Crew

fun GetMovieCreditsResponse.Crew.toModel(): Crew {
    return Crew(
        adult,
        gender,
        id,
        knownForDepartment,
        name,
        originalName,
        popularity,
        profilePath,
        creditId,
        department,
        job
    )
}