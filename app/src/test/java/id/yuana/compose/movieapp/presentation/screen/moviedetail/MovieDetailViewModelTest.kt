@file:OptIn(ExperimentalCoroutinesApi::class)

package id.yuana.compose.movieapp.presentation.screen.moviedetail

import com.google.common.truth.Truth.assertThat
import id.yuana.compose.movieapp.data.MovieDataProvider
import id.yuana.compose.movieapp.data.mapper.toModel
import id.yuana.compose.movieapp.domain.usecase.AddRemoveMovieToFavoriteUseCase
import id.yuana.compose.movieapp.domain.usecase.GetMovieDetailUseCase
import id.yuana.compose.movieapp.domain.usecase.GetMovieVideosUseCase
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MovieDetailViewModelTest {


    private lateinit var getMovieDetailUseCase: GetMovieDetailUseCase
    private lateinit var getMovieVideosUseCase: GetMovieVideosUseCase
    private lateinit var addRemoveMovieToFavoriteUseCase: AddRemoveMovieToFavoriteUseCase

    private lateinit var movieDetailViewModel: MovieDetailViewModel

    val moviePopularResponse = MovieDataProvider.createGetMoviePopularResponse()
    val movieFromPopular = moviePopularResponse.results.first().toModel()
    val movieDetailResponse = MovieDataProvider.createGetMovieDetailResponse()
    val movieVideosResponse = MovieDataProvider.createGetMovieVideosResponse()
    val movieFlow = flowOf(movieDetailResponse.toModel())
    val movieVideosFlow = flowOf(movieVideosResponse.results.map { it.toModel() })

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())

        getMovieDetailUseCase = mockk(relaxed = true)
        getMovieVideosUseCase = mockk(relaxed = true)
        addRemoveMovieToFavoriteUseCase = mockk()

        movieDetailViewModel = MovieDetailViewModel(
            getMovieDetailUseCase, getMovieVideosUseCase, addRemoveMovieToFavoriteUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `given valid movieId then should return success`() = runTest {

        assertThat(MovieDetailState()).isEqualTo(movieDetailViewModel.uiState.value)

        coEvery { getMovieDetailUseCase(movieFromPopular.id) } returns movieFlow
        coEvery { getMovieVideosUseCase(movieFromPopular.id) } returns movieVideosFlow

        movieDetailViewModel.fetchDetail(movieFromPopular)
        assertThat(
            MovieDetailState(
                movie = movieFlow,
                videos = movieVideosFlow
            )
        ).isEqualTo(movieDetailViewModel.uiState.value)

        coVerify { getMovieDetailUseCase(movieFromPopular.id) }
        coVerify { getMovieVideosUseCase(movieFromPopular.id) }

    }

    @Test
    fun `given valid movie and toggle favorite then return success`() = runTest {

        val movieDetail = movieDetailResponse.toModel()

        coJustAwait { addRemoveMovieToFavoriteUseCase(movieDetail) }

        movieDetailViewModel.fetchDetail(movieFromPopular)
        movieDetailViewModel.addRemoveFavorite(movieDetail)

        coVerify { addRemoveMovieToFavoriteUseCase(movieDetail) }

    }
}