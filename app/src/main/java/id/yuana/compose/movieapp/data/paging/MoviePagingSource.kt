package id.yuana.compose.movieapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.yuana.compose.movieapp.data.mapper.toModel
import id.yuana.compose.movieapp.data.remote.MovieApi
import id.yuana.compose.movieapp.domain.model.Movie
import javax.inject.Inject

class MoviePagingSource @Inject constructor(
    private val movieApi: MovieApi
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> = try {
        val nextPageNumber = params.key ?: 1
        val response = movieApi.getMoviePopular(nextPageNumber, "en")
        LoadResult.Page(
            data = response.results.map { it.toModel() },
            prevKey = null,
            nextKey = response.page + 1
        )
    } catch (e: Exception) {
        LoadResult.Error(e)
    }
}