package id.yuana.compose.movieapp.data.remote

import id.yuana.compose.movieapp.data.remote.response.GetMovieCreditsResponse
import id.yuana.compose.movieapp.data.remote.response.GetMovieDetailResponse
import id.yuana.compose.movieapp.data.remote.response.GetMoviePopularResponse
import id.yuana.compose.movieapp.data.remote.response.GetMovieVideosResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/popular")
    suspend fun getMoviePopular(
        @Query("page") page: Int,
        @Query("language") language: String
    ): Result<GetMoviePopularResponse>

    @GET("movie/{movieId}")
    suspend fun getMovieDetail(
        @Path("movieId") movieId: Int
    ): Result<GetMovieDetailResponse>

    @GET("movie/{movieId}/videos")
    suspend fun getMovieVideos(
        @Path("movieId") movieId: Int
    ): Result<GetMovieVideosResponse>

    @GET("movie/{movieId}/credits")
    suspend fun getMovieCredits(
        @Path("movieId") movieId: Int
    ): Result<GetMovieCreditsResponse>
}