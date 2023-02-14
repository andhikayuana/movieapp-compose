@file:OptIn(ExperimentalCoroutinesApi::class)

package id.yuana.compose.movieapp.data.repository

import id.yuana.compose.movieapp.data.MovieDataProvider.createErrorNotFoundResponse
import id.yuana.compose.movieapp.data.MovieDataProvider.createGetMovieCreditsResponse
import id.yuana.compose.movieapp.data.MovieDataProvider.createGetMovieDetailResponse
import id.yuana.compose.movieapp.data.MovieDataProvider.createGetMovieVideosResponse
import id.yuana.compose.movieapp.data.local.database.MovieDatabase
import id.yuana.compose.movieapp.data.mapper.toEntity
import id.yuana.compose.movieapp.data.mapper.toModel
import id.yuana.compose.movieapp.data.remote.MovieApi
import id.yuana.compose.movieapp.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException

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
    fun `when getMoviePopular called, then should success`() = runTest {

//        val moviePopularPage1Response = MovieDataProvider.createGetMoviePopularResponse()
//        val movieRemotePagingSource = MovieRemotePagingSource(movieApi)

//        coEvery { movieApi.getMoviePopular(1, "en") } returns Result.success(moviePopularPage1Response)
//        coEvery { movieRemotePagingSource.load(PagingSource.LoadParams.Refresh(
//            key = 1,
//            loadSize = 20,
//            placeholdersEnabled = false
//        )) } returns

//        val moviePopularPager = movieRepository.getMoviePopular()

//        coVerify { movieApi.getMoviePopular(1, "en") }
    }


    @Test
    fun `given getMovieVideos with valid movieId then should return movie videos with success response`() =
        runTest {
            //given
            coEvery { movieApi.getMovieVideos(blackPantherId) } returns Result.success(
                createGetMovieVideosResponse()
            )

            //when
            val movieVideosActual = movieRepository.getMovieVideos(blackPantherId)

            //then
            coVerify { movieApi.getMovieVideos(blackPantherId) }

            val videoActual = movieVideosActual.first()

            assertEquals("https://img.youtube.com/vi/GR03EwYlVQM/0.jpg", videoActual.getYoutubeThumbnail())
        }

    @Test(expected = HttpException::class)
    fun `given getMovieVideos with invalid movieId then should return error`() = runTest {
        //given
        coEvery { movieApi.getMovieVideos(-1) } throws createErrorNotFoundResponse()

        //when
        val actual = movieRepository.getMovieVideos(-1)

        //then
        coVerify { movieApi.getMovieVideos(-1) }
        assertTrue(actual.isNotEmpty())

    }

    @Test
    fun `given getMovieCredits with valid movieId then should return success`() = runTest {

        coEvery { movieApi.getMovieCredits(blackPantherId) } returns Result.success(
            createGetMovieCreditsResponse()
        )

        val actual = movieRepository.getMovieCredits(blackPantherId)

        coVerify { movieApi.getMovieCredits(blackPantherId) }

        assertTrue(actual.first.isNotEmpty())
        assertTrue(actual.second.isNotEmpty())

        val castActual = actual.first.first()
        val crewActual = actual.second.first()

        assertEquals("https://image.tmdb.org/t/p/w500/i6fbYNn5jWA6swWtaqgzaj02RMc.jpg", castActual.getProfileImage())
        assertEquals("https://image.tmdb.org/t/p/w500/pI3OhmnHhXLEwuv0Vq6qJHivCJA.jpg", crewActual.getProfileImage())
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

        assertEquals("https://image.tmdb.org/t/p/w500/sv1xJUazXeYqALzczSZ3O6nkH75.jpg", movie.getPosterUrl())
        assertEquals("https://image.tmdb.org/t/p/w500/xDMIl84Qo5Tsu62c9DGWhmPI67A.jpg", movie.getBackdropUrl())

        assertEquals("2h 42m", movie.getDuration())
        assertEquals("2022", movie.getReleaseYear())
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
        coJustRun { movieDatabase.movieEntityDao().insertOrUpdate(blackPantherMovie.toEntity()) }

        //when
        val movie = movieRepository.addRemoveMovieFavorite(blackPantherMovie)

        //then
        coVerify { movieDatabase.movieEntityDao().insertOrUpdate(blackPantherMovie.toEntity()) }
        assertEquals(true, movie.favorite)
    }


}
