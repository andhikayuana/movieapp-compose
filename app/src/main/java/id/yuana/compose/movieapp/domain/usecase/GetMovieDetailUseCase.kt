package id.yuana.compose.movieapp.domain.usecase

import id.yuana.compose.movieapp.domain.model.Movie
import id.yuana.compose.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(id: Int): Flow<Movie> {
        return flowOf(movieRepository.getMovieDetail(id))
    }
}