package id.yuana.compose.movieapp.domain.repository

import id.yuana.compose.movieapp.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getMoviePopular(page: Int, language: String): Flow<List<Movie>>

    suspend fun getMovieDetail(movieId: Int)
}