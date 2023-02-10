package id.yuana.compose.movieapp.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import id.yuana.compose.movieapp.domain.model.Movie
import id.yuana.compose.movieapp.domain.model.Video
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMoviePopular(): Pager<Int, Movie>
    suspend fun getMovieDetail(movieId: Int): Movie
    suspend fun getMovieVideos(movieId: Int): List<Video>
    suspend fun addRemoveMovieFavorite(movie: Movie): Movie
}