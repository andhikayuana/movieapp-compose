package id.yuana.compose.movieapp.data.mapper

import id.yuana.compose.movieapp.data.remote.response.GetMovieDetailResponse
import id.yuana.compose.movieapp.domain.model.Genre

fun GetMovieDetailResponse.Genre.toModel() = Genre(id, name)