package id.yuana.compose.movieapp.data.repository

import id.yuana.compose.movieapp.data.mapper.toModel
import id.yuana.compose.movieapp.data.paging.MoviePagingSource
import id.yuana.compose.movieapp.data.remote.MovieApi
import id.yuana.compose.movieapp.domain.model.Movie
import id.yuana.compose.movieapp.domain.model.Video
import id.yuana.compose.movieapp.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val moviePagingSource: MoviePagingSource,
    private val movieApi: MovieApi
) : MovieRepository {

    override fun getMoviePopular(): MoviePagingSource =
        moviePagingSource

    override suspend fun getMovieDetail(movieId: Int): Movie =
        movieApi.getMovieDetail(movieId).toModel()

    override suspend fun getMovieVideos(movieId: Int): List<Video> {
        return movieApi.getMovieVideos(movieId).results.map { it.toModel() }
    }


}