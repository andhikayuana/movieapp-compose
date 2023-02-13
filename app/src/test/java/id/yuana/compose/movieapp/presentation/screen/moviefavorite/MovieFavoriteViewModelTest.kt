@file:OptIn(ExperimentalCoroutinesApi::class)

package id.yuana.compose.movieapp.presentation.screen.moviefavorite

import id.yuana.compose.movieapp.data.remote.MovieApi
import id.yuana.compose.movieapp.domain.usecase.GetMovieFavoriteUseCase
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class MovieFavoriteViewModelTest {

    private lateinit var movieApi: MovieApi
    private lateinit var getMovieFavoriteUseCase: GetMovieFavoriteUseCase
    private lateinit var movieFavoriteViewModel: MovieFavoriteViewModel

    @Before
    fun setUp() {
        movieApi = mockk()
        getMovieFavoriteUseCase = mockk(relaxed = true)
        movieFavoriteViewModel = MovieFavoriteViewModel(getMovieFavoriteUseCase)
    }

    @After
    fun tearDown() {

    }

    @Test
    fun `get movie favorites, then should return success`() = runTest {

        //todo
    }

}