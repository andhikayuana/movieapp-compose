package id.yuana.compose.movieapp.domain.usecase

import id.yuana.compose.movieapp.domain.repository.MovieRepository
import io.mockk.mockk
import org.junit.After
import org.junit.Before

class GetMoviePopularUseCaseTest {


    private lateinit var movieRepository: MovieRepository
    private lateinit var getMoviePopularUseCase: GetMoviePopularUseCase

    @Before
    fun setUp() {
        movieRepository = mockk()
        getMoviePopularUseCase = GetMoviePopularUseCase(movieRepository)
    }

    @After
    fun tearDown() {
    }

    //todo
}