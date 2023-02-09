package id.yuana.compose.movieapp.presentation.screen.moviepopular

import androidx.paging.PagingData
import id.yuana.compose.movieapp.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class MoviePopularState(
    val currentPage: Int = 1,
    val movies: Flow<PagingData<Movie>> = emptyFlow(),
)
