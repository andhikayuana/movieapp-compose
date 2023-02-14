package id.yuana.compose.movieapp.domain.repository

import androidx.paging.Pager
import id.yuana.compose.movieapp.data.local.database.entity.MovieEntity
import id.yuana.compose.movieapp.domain.model.Cast
import id.yuana.compose.movieapp.domain.model.Crew
import id.yuana.compose.movieapp.domain.model.Movie
import id.yuana.compose.movieapp.domain.model.Video

interface MovieRepository {
    fun getMoviePopular(): Pager<Int, MovieEntity>
    suspend fun getMovieDetail(movieId: Int): Movie
    suspend fun getMovieVideos(movieId: Int): List<Video>

    suspend fun getMovieCredits(movieId: Int): Pair<List<Cast>, List<Crew>>
    suspend fun addRemoveMovieFavorite(movie: Movie): Movie

    fun getMovieFavorite(): Pager<Int, MovieEntity>
}