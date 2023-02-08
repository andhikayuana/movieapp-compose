package id.yuana.compose.movieapp.presentation.screen.moviepopular

import id.yuana.compose.movieapp.domain.model.Movie

data class MoviePopularState(
    val currentPage: Int = 1,
    val movies: List<Movie> = emptyList()
)
