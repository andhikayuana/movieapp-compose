@file:OptIn(ExperimentalCoroutinesApi::class)

package id.yuana.compose.movieapp.domain.usecase

import id.yuana.compose.movieapp.data.MovieDataProvider
import id.yuana.compose.movieapp.data.mapper.toModel
import id.yuana.compose.movieapp.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetMovieCreditsUseCaseTest {

    private lateinit var movieRepository: MovieRepository
    private lateinit var getMovieCreditsUseCase: GetMovieCreditsUseCase

    @Before
    fun setUp() {
        movieRepository = mockk()
        getMovieCreditsUseCase = GetMovieCreditsUseCase(movieRepository)
    }

    @After
    fun tearDown() {

    }

    @Test
    fun `given valid movieId then return movie credits successfully`() = runTest {

        val movieCreditsResponse = MovieDataProvider.createGetMovieCreditsResponse()

        coEvery { movieRepository.getMovieCredits(movieCreditsResponse.id) } returns Pair(
            movieCreditsResponse.cast.map { it.toModel() },
            movieCreditsResponse.crew.map { it.toModel() }
        )

        val movieCredits = getMovieCreditsUseCase.invoke(movieCreditsResponse.id)
        val castActual = movieCredits.first.first()
        val crewActual = movieCredits.second.first()

        coVerify { movieRepository.getMovieCredits(movieCreditsResponse.id) }
        assertTrue(castActual.isNotEmpty())
        assertTrue(crewActual.isNotEmpty())
    }
}