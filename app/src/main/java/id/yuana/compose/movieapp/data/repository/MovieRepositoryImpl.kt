package id.yuana.compose.movieapp.data.repository

import id.yuana.compose.movieapp.data.mapper.toModel
import id.yuana.compose.movieapp.data.remote.MovieApi
import id.yuana.compose.movieapp.domain.model.Movie
import id.yuana.compose.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi
) : MovieRepository {

    override suspend fun getMoviePopular(page: Int, language: String): Flow<List<Movie>> {
        return flow {
            emit(movieApi.getMoviePopular(page, language).results.map {
                it.toModel()
            })
        }
    }

    override suspend fun getMovieDetail(movieId: Int): Flow<Movie> {
        TODO("Not yet implemented")
    }


}