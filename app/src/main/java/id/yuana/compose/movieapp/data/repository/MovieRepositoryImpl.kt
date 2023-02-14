@file:OptIn(ExperimentalPagingApi::class)

package id.yuana.compose.movieapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import id.yuana.compose.movieapp.data.local.database.MovieDatabase
import id.yuana.compose.movieapp.data.local.database.entity.MovieEntity
import id.yuana.compose.movieapp.data.mapper.toEntity
import id.yuana.compose.movieapp.data.mapper.toModel
import id.yuana.compose.movieapp.data.paging.MovieRemoteMediator
import id.yuana.compose.movieapp.data.remote.MovieApi
import id.yuana.compose.movieapp.domain.model.Cast
import id.yuana.compose.movieapp.domain.model.Crew
import id.yuana.compose.movieapp.domain.model.Movie
import id.yuana.compose.movieapp.domain.model.Video
import id.yuana.compose.movieapp.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDatabase: MovieDatabase
) : MovieRepository {
    override fun getMoviePopular(): Pager<Int, MovieEntity> = Pager(
        config = PagingConfig(
            pageSize = 20
        ),
        pagingSourceFactory = {
            //not testable?
//            MovieRemotePagingSource(movieApi)
            movieDatabase.movieEntityDao().paginate()
        },
        remoteMediator = MovieRemoteMediator(movieApi, movieDatabase)
    )


    override suspend fun getMovieDetail(movieId: Int): Movie {
        val local = movieDatabase.movieEntityDao().find(movieId)?.toModel()
        val api = movieApi.getMovieDetail(movieId).getOrThrow().toModel()

        return if (local != null) {
            api.copy(favorite = true)
        } else {
            api
        }
    }

    override suspend fun getMovieVideos(movieId: Int): List<Video> {
        return movieApi.getMovieVideos(movieId).getOrThrow().results.map { it.toModel() }
    }

    override suspend fun getMovieCredits(movieId: Int): Pair<List<Cast>, List<Crew>> {
        val creditsResult = movieApi.getMovieCredits(movieId).getOrThrow()
        return Pair(
            creditsResult.cast.map { it.toModel() },
            creditsResult.crew.map { it.toModel() }
        )
    }

    override suspend fun addRemoveMovieFavorite(movie: Movie): Movie {
        if (movie.favorite) {
            movieDatabase.movieEntityDao().delete(movie.id)
        } else {
            movieDatabase.movieEntityDao().insertOrUpdate(movie.toEntity())
        }
        return movie.copy(favorite = !movie.favorite)
    }

    override fun getMovieFavorite(): Pager<Int, MovieEntity> = Pager(
        config = PagingConfig(pageSize = 20)
    ) {
        movieDatabase.movieEntityDao().paginate()
    }


}