package id.yuana.compose.movieapp.data.mapper

import id.yuana.compose.movieapp.data.remote.response.GetMovieVideosResponse
import id.yuana.compose.movieapp.domain.model.Video

fun GetMovieVideosResponse.Result.toModel(): Video = Video(
    id = id,
    name = name,
    type = type,
    site = site,
    key = key
)