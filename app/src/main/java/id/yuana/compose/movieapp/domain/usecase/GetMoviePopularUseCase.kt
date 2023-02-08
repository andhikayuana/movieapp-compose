package id.yuana.compose.movieapp.domain.usecase

import id.yuana.compose.movieapp.domain.model.Movie
import id.yuana.compose.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviePopularUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(
        page: Int,
        language: String
    ): Flow<List<Movie>> {
        return movieRepository.getMoviePopular(page, language)
    }
}