package id.yuana.compose.movieapp.presentation.screen.moviedetail

import id.yuana.compose.movieapp.domain.model.Movie
import id.yuana.compose.movieapp.domain.model.Video
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class MovieDetailState(
    val movie: Flow<Movie> = emptyFlow(),
    val videos: Flow<List<Video>> = emptyFlow()
)