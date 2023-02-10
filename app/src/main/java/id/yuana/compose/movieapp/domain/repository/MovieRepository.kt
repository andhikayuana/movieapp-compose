package id.yuana.compose.movieapp.domain.repository

import androidx.paging.Pager
import id.yuana.compose.movieapp.data.local.database.MovieEntity
import id.yuana.compose.movieapp.domain.model.Movie
import id.yuana.compose.movieapp.domain.model.Video

interface MovieRepository {
    fun getMoviePopular(): Pager<Int, Movie>
    suspend fun getMovieDetail(movieId: Int): Movie
    suspend fun getMovieVideos(movieId: Int): List<Video>
    suspend fun addRemoveMovieFavorite(movie: Movie): Movie

    fun getMovieFavorite(): Pager<Int, MovieEntity>
}