package id.yuana.compose.movieapp.domain.usecase

import id.yuana.compose.movieapp.domain.repository.MovieRepository
import io.mockk.mockk
import org.junit.After
import org.junit.Before

class GetMovieFavoriteUseCaseTest {

    private lateinit var movieRepository: MovieRepository
    private lateinit var getMovieFavoriteUseCase: GetMovieFavoriteUseCase

    @Before
    fun setUp() {
        movieRepository = mockk()
        getMovieFavoriteUseCase = GetMovieFavoriteUseCase(movieRepository)
    }

    @After
    fun tearDown() {
    }

    //todo

}