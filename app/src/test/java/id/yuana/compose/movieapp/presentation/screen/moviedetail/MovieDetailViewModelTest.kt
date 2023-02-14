@file:OptIn(ExperimentalCoroutinesApi::class)

package id.yuana.compose.movieapp.presentation.screen.moviedetail

import com.google.common.truth.Truth.assertThat
import id.yuana.compose.movieapp.data.MovieDataProvider
import id.yuana.compose.movieapp.data.mapper.toModel
import id.yuana.compose.movieapp.domain.usecase.*
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MovieDetailViewModelTest {


    private lateinit var getMovieDetailUseCase: GetMovieDetailUseCase
    private lateinit var getMovieVideosUseCase: GetMovieVideosUseCase
    private lateinit var addRemoveMovieToFavoriteUseCase: AddRemoveMovieToFavoriteUseCase
    private lateinit var getMovieCreditsUseCase: GetMovieCreditsUseCase

    private lateinit var movieDetailViewModel: MovieDetailViewModel

    val moviePopularResponse = MovieDataProvider.createGetMoviePopularResponse()
    val movieFromPopular = moviePopularResponse.results.first().toModel()

    val movieDetailResponse = MovieDataProvider.createGetMovieDetailResponse()
    val movieVideosResponse = MovieDataProvider.createGetMovieVideosResponse()
    val movieCreditsResponse = MovieDataProvider.createGetMovieCreditsResponse()

    val movieFlow = flowOf(movieDetailResponse.toModel())
    val movieVideosFlow = flowOf(movieVideosResponse.results.map { it.toModel() })
    val movieCreditsFlow = movieCreditsResponse.let {
        Pair(
            flowOf( it.cast.map { it.toModel() }),
            flowOf(it.crew.map { it.toModel() })
        )
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())

        getMovieDetailUseCase = mockk(relaxed = true)
        getMovieVideosUseCase = mockk(relaxed = true)
        addRemoveMovieToFavoriteUseCase = mockk()
        getMovieCreditsUseCase = mockk()

        movieDetailViewModel = MovieDetailViewModel(
            getMovieDetailUseCase,
            getMovieVideosUseCase,
            addRemoveMovieToFavoriteUseCase,
            getMovieCreditsUseCase
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
        coEvery { getMovieCreditsUseCase(movieFromPopular.id) } returns movieCreditsFlow

        movieDetailViewModel.fetchDetail(movieFromPopular)
        assertThat(
            MovieDetailState(
                movie = movieFlow,
                videos = movieVideosFlow,
                cast = movieCreditsFlow.first,
                crew = movieCreditsFlow.second
            )
        ).isEqualTo(movieDetailViewModel.uiState.value)

        coVerify { getMovieDetailUseCase(movieFromPopular.id) }
        coVerify { getMovieVideosUseCase(movieFromPopular.id) }
        coVerify { getMovieCreditsUseCase(movieFromPopular.id) }

    }

    @Test
    fun `given valid movie and toggle favorite then return success`() = runTest {

        val movieDetail = movieDetailResponse.toModel().copy(favorite = true)

        coEvery { addRemoveMovieToFavoriteUseCase(movieDetail) } returns flowOf(movieDetail)

        movieDetailViewModel.fetchDetail(movieFromPopular)
        movieDetailViewModel.addRemoveFavorite(movieDetail)

        coVerify { addRemoveMovieToFavoriteUseCase(movieDetail) }
        assertEquals(true, movieDetailViewModel.uiState.value.movie.last().favorite)

    }
}