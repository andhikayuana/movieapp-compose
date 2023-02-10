package id.yuana.compose.movieapp.data.remote

import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import id.yuana.compose.movieapp.data.remote.response.GeneralErrorResponse
import id.yuana.compose.movieapp.data.remote.response.parsedHttpError
import id.yuana.compose.movieapp.di.RemoteModule
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class MovieApiTest {

    companion object {
        const val baseUrl = "http://localhost:9090"
        const val port = 9090
        const val getMoviePopularPage1Response = "movie_popular_response_page1.json"
        const val getMoviePopularPage2Response = "movie_popular_response_page2.json"
        const val getMoviePopularPage3Response = "movie_popular_response_page3.json"
        const val getMovieFailedApiKeyResponse = "movie_failed_api_key_response.json"
        const val getMovieNotFoundResponse = "movie_not_found_response.json"
        const val getMovieDetailResponse = "movie_detail_response.json"
        const val getMovieVideosResponse = "movie_videos_response.json"
    }

    private lateinit var mockWebServer: MockWebServer
    private lateinit var movieApi: MovieApi

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start(port)

        val okHttpClient = RemoteModule.provideOkHttpClient()
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ResultCallAdapterFactory.create())
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()

        movieApi = RemoteModule.provideMovieApi(retrofit)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    private fun createMockResponse(
        fileName: String,
        responseCode: Int = HttpURLConnection.HTTP_OK
    ) = MockResponse()
        .setResponseCode(responseCode)
        .setBody(MockResponseFileReader(fileName).content)

    @Test
    fun `when getMoviePopular page1 should return success`() {

        //given
        mockWebServer.enqueue(createMockResponse(getMoviePopularPage1Response))

        //when
        val actualResponse = runBlocking {
            movieApi.getMoviePopular(1, "en")
        }.getOrNull()

        //then
        actualResponse?.let { actual ->
            assertNotNull(actual)
            assertEquals(1, actual.page)
            assertEquals(20, actual.results.size)
            assertTrue(actual.results.isNotEmpty())
            assertTrue(actual.totalPages > 0)
            assertTrue(actual.totalResults > 0)

            val movie = actual.results.first()
            assertEquals(false, movie.adult)
            assertEquals(505642, movie.id)
            assertEquals(2763, movie.voteCount)
            assertEquals("Black Panther: Wakanda Forever", movie.title)
        }

    }

    @Test
    fun `when getMoviePopular page2 should return success`() {

        //given
        mockWebServer.enqueue(createMockResponse(getMoviePopularPage2Response))

        //when
        val actualResponse = runBlocking {
            movieApi.getMoviePopular(2, "en")
        }.getOrNull()

        //then
        assertNotNull(actualResponse)
        assertEquals(2, actualResponse?.page)
        assertEquals(20, actualResponse?.results?.size)
    }

    @Test
    fun `when getMoviePopular page3 should return success`() {

        //given
        mockWebServer.enqueue(createMockResponse(getMoviePopularPage3Response))

        //when
        val actualResponse = runBlocking {
            movieApi.getMoviePopular(3, "en")
        }.getOrNull()

        //then
        assertNotNull(actualResponse)
        assertEquals(3, actualResponse?.page)
        assertEquals(20, actualResponse?.results?.size)
    }

    @Test
    fun `when getMoviePopular with failed api key should return error`() {

        //given
        mockWebServer.enqueue(
            createMockResponse(
                getMovieFailedApiKeyResponse,
                responseCode = HttpURLConnection.HTTP_UNAUTHORIZED
            )
        )

        //when
        val actualResponse = runBlocking {
            movieApi.getMoviePopular(1, "en")
        }.exceptionOrNull()
        val error = actualResponse?.parsedHttpError<GeneralErrorResponse>()

        //then
        assertNotNull(error)
        assertEquals(7, error?.statusCode)
        assertEquals(false, error?.success)
    }

    @Test
    fun `when getMoviePopular with wrong path should return error`() {

        //given
        mockWebServer.enqueue(
            createMockResponse(
                getMovieNotFoundResponse,
                responseCode = HttpURLConnection.HTTP_NOT_FOUND
            )
        )

        //when
        val actualResponse = runBlocking {
            movieApi.getMoviePopular(1, "en")
        }.exceptionOrNull()
        val error = actualResponse?.parsedHttpError<GeneralErrorResponse>()

        //then
        assertNotNull(error)
        assertEquals(34, error?.statusCode)
        assertEquals(false, error?.success)
        assertEquals("The resource you requested could not be found.", error?.statusMessage)
    }

    @Test
    fun `when getMovieDetail valid movieId should return success`() {

        //given
        val movieId = 505642
        mockWebServer.enqueue(createMockResponse(getMovieDetailResponse))

        //when
        val actualResponse = runBlocking {
            movieApi.getMovieDetail(movieId)
        }.getOrNull()

        //then
        actualResponse?.let { actual ->
            assertNotNull(actual)
            assertEquals(movieId, actual.id)
            assertEquals("Black Panther: Wakanda Forever", actual.title)

            val genre = actual.genres.first()
            assertEquals(28, genre.id)
            assertEquals("Action", genre.name)

            val productionCompany = actual.productionCompanies.first()
            assertEquals(420, productionCompany.id)
            assertEquals("Marvel Studios", productionCompany.name)
            assertEquals("US", productionCompany.originCountry)

            val productionCountry = actual.productionCountries.first()
            assertEquals("US", productionCountry.iso31661)
            assertEquals("United States of America", productionCountry.name)

            val spokenLanguage = actual.spokenLanguages.first()
            assertEquals("English", spokenLanguage.name)
            assertEquals("en", spokenLanguage.iso6391)
        }

    }

    @Test
    fun `when getMovieDetail invalid movieId should return error`() {

        //given
        val movieId = 99999
        mockWebServer.enqueue(
            createMockResponse(
                getMovieNotFoundResponse,
                HttpURLConnection.HTTP_NOT_FOUND
            )
        )

        //when
        val actualResponse = runBlocking {
            movieApi.getMovieDetail(movieId)
        }.exceptionOrNull()
        val error = actualResponse?.parsedHttpError<GeneralErrorResponse>()

        //then
        assertNotNull(error)
        assertEquals(34, error?.statusCode)
        assertEquals(false, error?.success)
        assertEquals("The resource you requested could not be found.", error?.statusMessage)
    }

    @Test
    fun `when getMovieVideos valid movieId should return success`() {

        //given
        val movieId = 505642
        mockWebServer.enqueue(createMockResponse(getMovieVideosResponse))

        //when
        val actualResponse = runBlocking {
            movieApi.getMovieVideos(movieId)
        }.getOrNull()

        //then
        actualResponse?.let { actual ->
            assertNotNull(actual)
            assertEquals(movieId, actual.id)
            assertTrue(actual.results.isNotEmpty())

            val video = actual.results.first()
            assertEquals("Teaser", video.type)
            assertEquals("YouTube", video.site)
            assertEquals("GR03EwYlVQM", video.key)
        }


    }

}