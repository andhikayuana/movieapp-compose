package id.yuana.compose.movieapp.domain.repository

import id.yuana.compose.movieapp.data.paging.MovieRemotePagingSource
import id.yuana.compose.movieapp.domain.model.Movie
import id.yuana.compose.movieapp.domain.model.Video

interface MovieRepository {

    fun getMoviePopular(): MovieRemotePagingSource

    suspend fun getMovieDetail(movieId: Int): Movie

    suspend fun getMovieVideos(movieId: Int): List<Video>

    suspend fun addRemoveMovieFavorite(movie: Movie): Movie
}