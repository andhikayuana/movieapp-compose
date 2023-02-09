package id.yuana.compose.movieapp.domain.usecase

import id.yuana.compose.movieapp.domain.model.Video
import id.yuana.compose.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetMovieVideosUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): Flow<List<Video>> {
        return flowOf(movieRepository.getMovieVideos(movieId))
    }
}