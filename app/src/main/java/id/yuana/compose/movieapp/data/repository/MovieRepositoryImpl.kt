package id.yuana.compose.movieapp.data.repository

import id.yuana.compose.movieapp.data.local.database.MovieDatabase
import id.yuana.compose.movieapp.data.mapper.toEntity
import id.yuana.compose.movieapp.data.mapper.toModel
import id.yuana.compose.movieapp.data.paging.MovieRemotePagingSource
import id.yuana.compose.movieapp.data.remote.MovieApi
import id.yuana.compose.movieapp.domain.model.Movie
import id.yuana.compose.movieapp.domain.model.Video
import id.yuana.compose.movieapp.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieRemotePagingSource: MovieRemotePagingSource,
    private val movieApi: MovieApi,
    private val movieDatabase: MovieDatabase
) : MovieRepository {

    override fun getMoviePopular(): MovieRemotePagingSource =
        movieRemotePagingSource

    override suspend fun getMovieDetail(movieId: Int): Movie {
        val local = movieDatabase.movieEntityDao().find(movieId)?.toModel()
        val api = movieApi.getMovieDetail(movieId).toModel()

        return if (local != null) {
            api.copy(favorite = true)
        } else {
            api
        }
    }

    override suspend fun getMovieVideos(movieId: Int): List<Video> {
        return movieApi.getMovieVideos(movieId).results.map { it.toModel() }
    }

    override suspend fun addRemoveMovieFavorite(movie: Movie): Movie {
        if (movie.favorite) {
            movieDatabase.movieEntityDao().delete(movie.id)
        } else {
            movieDatabase.movieEntityDao().insert(movie.toEntity())
        }
        return movie.copy(favorite = !movie.favorite)
    }

}