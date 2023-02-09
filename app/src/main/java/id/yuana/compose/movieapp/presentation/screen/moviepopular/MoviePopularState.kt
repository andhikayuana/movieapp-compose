package id.yuana.compose.movieapp.presentation.screen.moviepopular

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import id.yuana.compose.movieapp.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Stable
data class MoviePopularState(
    val movies: Flow<PagingData<Movie>> = emptyFlow(),
    val loading: Boolean = false
)
