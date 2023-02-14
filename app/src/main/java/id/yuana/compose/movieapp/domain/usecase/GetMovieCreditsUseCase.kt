package id.yuana.compose.movieapp.domain.usecase

import id.yuana.compose.movieapp.domain.model.Cast
import id.yuana.compose.movieapp.domain.model.Crew
import id.yuana.compose.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetMovieCreditsUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): Pair<Flow<List<Cast>>, Flow<List<Crew>>> {
        val movieCredits = movieRepository.getMovieCredits(movieId)
        return Pair(flowOf(movieCredits.first), flowOf(movieCredits.second))
    }
}