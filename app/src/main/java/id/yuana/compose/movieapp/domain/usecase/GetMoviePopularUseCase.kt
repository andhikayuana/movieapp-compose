package id.yuana.compose.movieapp.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import id.yuana.compose.movieapp.domain.model.Movie
import id.yuana.compose.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviePopularUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(): Flow<PagingData<Movie>> = Pager(
        PagingConfig(pageSize = 20)
    ) {
        movieRepository.getMoviePopular()
    }.flow
}