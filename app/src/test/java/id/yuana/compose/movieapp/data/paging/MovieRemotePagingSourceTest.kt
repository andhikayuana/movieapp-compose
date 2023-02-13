@file:OptIn(ExperimentalCoroutinesApi::class)

package id.yuana.compose.movieapp.data.paging

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.yuana.compose.movieapp.data.MovieDataProvider
import id.yuana.compose.movieapp.data.mapper.toModel
import id.yuana.compose.movieapp.data.remote.MovieApi
import id.yuana.compose.movieapp.data.remote.MovieApiTest
import id.yuana.compose.movieapp.domain.model.Movie
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MovieRemotePagingSourceTest {

    private lateinit var movieApi: MovieApi

    @Before
    fun setUp() {
        movieApi = mockk()
    }

    @After
    fun tearDown() {

    }

    @Test
    fun `when pagingSource load with valid params, then should return success`() = runTest {

        val moviePopularResponse = MovieDataProvider.createGetMoviePopularResponse()
        val movieRemotePagingSource = MovieRemotePagingSource(movieApi)

        coEvery { movieApi.getMoviePopular(1, "en") } returns Result.success(moviePopularResponse)

        val actual = movieRemotePagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 1,
                20,
                false
            )
        )

        assertEquals(
            PagingSource.LoadResult.Page(
                data = moviePopularResponse.results.map { it.toModel() },
                prevKey = null,
                nextKey = 2
            ),
            actual
        )
        coVerify { movieApi.getMoviePopular(1, "en") }

    }

    @Test
    fun `when pagingSource load with invalid params, then should return error`() = runTest {

        val errorNotFoundException = MovieDataProvider.createErrorNotFoundResponse()
        val movieRemotePagingSource = MovieRemotePagingSource(movieApi)

        coEvery { movieApi.getMoviePopular(1, "en") } returns Result.failure(errorNotFoundException)

        val actual = movieRemotePagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 1,
                20,
                false
            )
        )

        assertEquals(PagingSource.LoadResult.Error<Int, Movie>(errorNotFoundException), actual)
        coVerify { movieApi.getMoviePopular(1, "en") }
    }

    @Test
    fun `when pagingSource load with valid params and need trigger next page, should return success`() =
        runTest {

            val movieRemotePagingSource = MovieRemotePagingSource(movieApi)

            val moviePopularPage1Response = MovieDataProvider.createGetMoviePopularResponse()
            val moviePopularPage2Response =
                MovieDataProvider.createGetMoviePopularResponse(MovieApiTest.getMoviePopularPage2Response)

            coEvery { movieApi.getMoviePopular(1, "en") } returns Result.success(
                moviePopularPage1Response
            )
            coEvery { movieApi.getMoviePopular(2, "en") } returns Result.success(
                moviePopularPage2Response
            )

            val actualPage1 = movieRemotePagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 1,
                    loadSize = 20,
                    placeholdersEnabled = false
                )
            )
            val actualPage2 = movieRemotePagingSource.load(
                PagingSource.LoadParams.Append(
                    key = 2,
                    loadSize = 20,
                    placeholdersEnabled = false
                )
            )

            assertEquals(
                PagingSource.LoadResult.Page(
                    data = moviePopularPage1Response.results.map { it.toModel() },
                    prevKey = null,
                    nextKey = 2
                ),
                actualPage1
            )

            assertEquals(
                PagingSource.LoadResult.Page(
                    data = moviePopularPage2Response.results.map { it.toModel() },
                    prevKey = null,
                    nextKey = 3
                ),
                actualPage2
            )

            coVerifyOrder {
                movieApi.getMoviePopular(1, "en")
                movieApi.getMoviePopular(2, "en")
            }
        }

    @Test
    fun `when getRefreshKey called, should return success`() = runTest {
        val movieRemotePagingSource = MovieRemotePagingSource(movieApi)

        val pagingState = PagingState<Int, Movie>(listOf(), 1, PagingConfig(20), 20)
        val refreshKeyActual = movieRemotePagingSource.getRefreshKey(
            state = pagingState
        )

        assertEquals(null, refreshKeyActual)


    }
}