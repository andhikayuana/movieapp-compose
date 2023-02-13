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
import org.junit.Before
import org.junit.Test

class AddRemoveMovieToFavoriteUseCaseTest {

    private lateinit var movieRepository: MovieRepository
    private lateinit var addRemoveMovieToFavoriteUseCase: AddRemoveMovieToFavoriteUseCase

    @Before
    fun setUp() {
        movieRepository = mockk()
        addRemoveMovieToFavoriteUseCase = AddRemoveMovieToFavoriteUseCase(movieRepository)
    }

    @After
    fun tearDown() {

    }

    @Test
    fun `toggle favorite move should success`() = runTest {

        val movieDetail = MovieDataProvider.createGetMovieDetailResponse().toModel()
        val movieDetailFavorite = movieDetail.copy(favorite = true)

        coEvery { movieRepository.addRemoveMovieFavorite(movieDetail) } returns movieDetailFavorite

        val movieActual = addRemoveMovieToFavoriteUseCase(movieDetail)

        coVerify { movieRepository.addRemoveMovieFavorite(movieDetail) }
        assertThat(movieActual.first()).isEqualTo(movieDetailFavorite)
    }
}