@file:OptIn(ExperimentalCoroutinesApi::class)

package id.yuana.compose.movieapp.presentation.screen.moviepopular

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import id.yuana.compose.movieapp.data.MovieDataProvider
import id.yuana.compose.movieapp.data.paging.MovieRemotePagingSource
import id.yuana.compose.movieapp.data.remote.MovieApi
import id.yuana.compose.movieapp.domain.model.Movie
import id.yuana.compose.movieapp.domain.usecase.GetMoviePopularUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MoviePopularViewModelTest {

    private lateinit var movieApi: MovieApi
    private lateinit var getPopularMoviePopularUseCase: GetMoviePopularUseCase
    private lateinit var moviePopularViewModel: MoviePopularViewModel

    @Before
    fun setUp(){
        movieApi = mockk()
        getPopularMoviePopularUseCase = mockk(relaxed = true)
        moviePopularViewModel = MoviePopularViewModel(getPopularMoviePopularUseCase)
    }

    @After
    fun tearDown(){

    }

    @Test
    fun `get movies then should return success`() = runTest {

        val pagerFlow = Pager(
            config = PagingConfig(
                pageSize = 20,
            ),
            pagingSourceFactory = {
                MovieRemotePagingSource(movieApi)
            }
        ).flow

        coEvery { getPopularMoviePopularUseCase() } returns pagerFlow

        moviePopularViewModel.movies

        coVerify { getPopularMoviePopularUseCase() }
    }
}