@file:OptIn(ExperimentalCoroutinesApi::class)

package id.yuana.compose.movieapp.data.repository

import com.google.gson.Gson
import id.yuana.compose.movieapp.data.MovieDataProvider.createErrorNotFoundResponse
import id.yuana.compose.movieapp.data.MovieDataProvider.createGetMovieDetailResponse
import id.yuana.compose.movieapp.data.MovieDataProvider.createGetMovieVideosResponse
import id.yuana.compose.movieapp.data.local.database.MovieDatabase
import id.yuana.compose.movieapp.data.mapper.toEntity
import id.yuana.compose.movieapp.data.mapper.toModel
import id.yuana.compose.movieapp.data.remote.MockResponseFileReader
import id.yuana.compose.movieapp.data.remote.MovieApi
import id.yuana.compose.movieapp.data.remote.MovieApiTest
import id.yuana.compose.movieapp.data.remote.response.GetMovieDetailResponse
import id.yuana.compose.movieapp.data.remote.response.GetMovieVideosResponse
import id.yuana.compose.movieapp.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.net.HttpURLConnection

class MovieRepositoryTest {

    companion object {
        const val blackPantherId = 505642
    }

    private lateinit var movieApi: MovieApi
    private lateinit var movieDatabase: MovieDatabase
    private lateinit var movieRepository: MovieRepository

    @Before
    fun setUp() {
        movieApi = mockk()
        movieDatabase = mockk()

        movieRepository = MovieRepositoryImpl(
            movieApi = movieApi,
            movieDatabase = movieDatabase
        )
    }

    @After
    fun tearDown() {

    }


    @Test
    fun `given getMovieVideos with valid movieId then should return movie videos with success response`() =
        runTest {
            //given
            coEvery { movieApi.getMovieVideos(blackPantherId) } returns Result.success(
                createGetMovieVideosResponse()
            )

            //when
            movieRepository.getMovieVideos(blackPantherId)

            //then
            coVerify { movieApi.getMovieVideos(blackPantherId) }
        }

    @Test(expected = HttpException::class)
    fun `given getMovieVideos with invalid movieId then should return error`() = runTest {
        //given
        coEvery { movieApi.getMovieVideos(-1) } throws createErrorNotFoundResponse()

        //when
        movieRepository.getMovieVideos(-1)

        //then
        coVerify { movieApi.getMovieVideos(-1) }

    }

    @Test
    fun `given getMovieDetail with valid movieId then should return success`() = runTest {
        //given
        coEvery { movieDatabase.movieEntityDao().find(blackPantherId) } returns null
        coEvery { movieApi.getMovieDetail(blackPantherId) } returns Result.success(
            createGetMovieDetailResponse()
        )

        //when
        val movie = movieRepository.getMovieDetail(blackPantherId)

        //then
        coVerify { movieDatabase.movieEntityDao().find(blackPantherId) }
        coVerify { movieApi.getMovieDetail(blackPantherId) }

        assertEquals(blackPantherId, movie.id)
        assertEquals(false, movie.favorite)
    }

    @Test
    fun `given getMovieDetail with valid movieId then should return success with favorite`() =
        runTest {
            //given
            coEvery {
                movieDatabase.movieEntityDao().find(blackPantherId)
            } returns createGetMovieDetailResponse().toModel().toEntity()
            coEvery { movieApi.getMovieDetail(blackPantherId) } returns Result.success(
                createGetMovieDetailResponse()
            )

            //when
            val movie = movieRepository.getMovieDetail(blackPantherId)

            //then
            coVerify { movieDatabase.movieEntityDao().find(blackPantherId) }
            coVerify { movieApi.getMovieDetail(blackPantherId) }

            assertEquals(blackPantherId, movie.id)
            assertEquals(true, movie.favorite)
        }

    @Test
    fun `given movie with favorite true then return success with unfavorite movie`() = runTest {
        //given
        val blackPantherMovie = createGetMovieDetailResponse().toModel().copy(favorite = true)
        coJustRun { movieDatabase.movieEntityDao().delete(blackPantherMovie.id) }

        //when
        val movie = movieRepository.addRemoveMovieFavorite(blackPantherMovie)

        //then
        coVerify { movieDatabase.movieEntityDao().delete(blackPantherMovie.id) }
        assertEquals(false, movie.favorite)
    }

    @Test
    fun `given movie with favorite false then return success with favorite movie`() = runTest {
        //given
        val blackPantherMovie = createGetMovieDetailResponse().toModel()
        coJustRun { movieDatabase.movieEntityDao().insert(blackPantherMovie.toEntity()) }

        //when
        val movie = movieRepository.addRemoveMovieFavorite(blackPantherMovie)

        //then
        coVerify { movieDatabase.movieEntityDao().insert(blackPantherMovie.toEntity()) }
        assertEquals(true, movie.favorite)
    }
}
