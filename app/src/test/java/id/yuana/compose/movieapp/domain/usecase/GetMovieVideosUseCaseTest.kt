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
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetMovieVideosUseCaseTest {

    private lateinit var movieRepository: MovieRepository
    private lateinit var getMovieVideosUseCase: GetMovieVideosUseCase

    @Before
    fun setUp(){
        movieRepository = mockk()
        getMovieVideosUseCase = GetMovieVideosUseCase(movieRepository)
    }

    @After
    fun tearDown(){

    }

    @Test
    fun `given valid movieId then return movie videos successfully`() = runTest {

        val movieDetail = MovieDataProvider.createGetMovieDetailResponse().toModel()
        val movieVideos = MovieDataProvider.createGetMovieVideosResponse().results.map { it.toModel() }

        coEvery { movieRepository.getMovieVideos(movieDetail.id) } returns movieVideos

        val movieVideosActual = getMovieVideosUseCase(movieDetail.id)

        coVerify { movieRepository.getMovieVideos(movieDetail.id) }
        assertEquals(movieVideos.size, movieVideosActual.first().size)

    }
}