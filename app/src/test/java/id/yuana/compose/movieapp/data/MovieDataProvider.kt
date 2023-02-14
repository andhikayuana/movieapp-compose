package id.yuana.compose.movieapp.data

import com.google.gson.Gson
import id.yuana.compose.movieapp.data.remote.MockResponseFileReader
import id.yuana.compose.movieapp.data.remote.MovieApi
import id.yuana.compose.movieapp.data.remote.MovieApiTest
import id.yuana.compose.movieapp.data.remote.response.GetMovieCreditsResponse
import id.yuana.compose.movieapp.data.remote.response.GetMovieDetailResponse
import id.yuana.compose.movieapp.data.remote.response.GetMoviePopularResponse
import id.yuana.compose.movieapp.data.remote.response.GetMovieVideosResponse
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.net.HttpURLConnection

object MovieDataProvider {

    private val gson = Gson()

    fun createGetMoviePopularResponse(
        file: String = MovieApiTest.getMoviePopularPage1Response
    ): GetMoviePopularResponse {
        val json = MockResponseFileReader(file).content
        return gson.fromJson(json, GetMoviePopularResponse::class.java)
    }

    fun createGetMovieVideosResponse(): GetMovieVideosResponse {
        val json = MockResponseFileReader(MovieApiTest.getMovieVideosResponse).content
        return gson.fromJson(json, GetMovieVideosResponse::class.java)
    }

    fun createGetMovieCreditsResponse(): GetMovieCreditsResponse {
        val json = MockResponseFileReader(MovieApiTest.getMovieCreditsResponse).content
        return gson.fromJson(json, GetMovieCreditsResponse::class.java)
    }

    fun createGetMovieDetailResponse(): GetMovieDetailResponse {
        val json = MockResponseFileReader(MovieApiTest.getMovieDetailResponse).content
        return gson.fromJson(json, GetMovieDetailResponse::class.java)
    }

    fun createErrorNotFoundResponse(): HttpException {
        val json = MockResponseFileReader(MovieApiTest.getMovieNotFoundResponse).content
        return HttpException(
            Response.error<HttpException>(
                HttpURLConnection.HTTP_NOT_FOUND,
                json.toResponseBody()
            )
        )
    }
}