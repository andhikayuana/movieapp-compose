@file:OptIn(ExperimentalCoroutinesApi::class)

package id.yuana.compose.movieapp.domain.usecase

import com.google.common.truth.Truth.assertThat
import id.yuana.compose.movieapp.data.MovieDataProvider
import id.yuana.compose.movieapp.data.mapper.toModel
import id.yuana.compose.movieapp.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class GetMovieDetailUseCaseTest {

    private lateinit var movieRepository: MovieRepository
    private lateinit var getMovieDetailUseCase: GetMovieDetailUseCase

    @Before
    fun setUp() {
        movieRepository = mockk()
        getMovieDetailUseCase = GetMovieDetailUseCase(movieRepository)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `given valid movieFromPopular then should return movieDetail successfully`() = runTest {

        val movieFromPopular = MovieDataProvider.createGetMoviePopularResponse().results.first()
        val movieDetail = MovieDataProvider.createGetMovieDetailResponse().toModel()

        coEvery { movieRepository.getMovieDetail(movieFromPopular.id) } returns movieDetail

        val actual = getMovieDetailUseCase(movieFromPopular.id)

        coVerify { movieRepository.getMovieDetail(movieFromPopular.id) }
        assertThat(movieDetail).isEqualTo(actual.first())
    }
}