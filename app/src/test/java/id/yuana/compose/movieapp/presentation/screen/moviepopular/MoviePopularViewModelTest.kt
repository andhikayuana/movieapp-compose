package id.yuana.compose.movieapp.presentation.screen.moviepopular

import id.yuana.compose.movieapp.domain.usecase.GetMoviePopularUseCase
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MoviePopularViewModelTest {

    private lateinit var getPopularMoviePopularUseCase: GetMoviePopularUseCase
    private lateinit var moviePopularViewModel: MoviePopularViewModel

    @Before
    fun setUp(){

        moviePopularViewModel
    }

    @After
    fun tearDown(){

    }

    @Test
    fun `test nganu`() {
        val actual = 4

        assertEquals(4, actual)
    }
}