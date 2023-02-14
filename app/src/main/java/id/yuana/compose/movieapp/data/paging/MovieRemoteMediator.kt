@file:OptIn(ExperimentalPagingApi::class)

package id.yuana.compose.movieapp.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import id.yuana.compose.movieapp.data.local.database.MovieDatabase
import id.yuana.compose.movieapp.data.local.database.entity.MovieEntity
import id.yuana.compose.movieapp.data.local.database.entity.RemoteKeyEntity
import id.yuana.compose.movieapp.data.mapper.toEntity
import id.yuana.compose.movieapp.data.mapper.toModel
import id.yuana.compose.movieapp.data.remote.MovieApi
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MovieRemoteMediator @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDatabase: MovieDatabase
) : RemoteMediator<Int, MovieEntity>() {

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)

        return if (System.currentTimeMillis() - (movieDatabase.remoteKeyEntityDao()
                .getCreationTime() ?: 0) < cacheTimeout
        ) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKey = getRemoteKeyClosestToCurrentPosition(state)
                remoteKey?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKey = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKey?.prevKey
                prevKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKey != null)
            }
            LoadType.APPEND -> {
                val remoteKey = getRemoteKeyForLastItem(state)
                val nextKey = remoteKey?.nextKey
                nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKey != null)
            }
        }

        return try {
            delay(500)
            val moviePopularResponse = movieApi.getMoviePopular(page, "en").getOrThrow()

            val movies = moviePopularResponse.results.map { it.toModel().toEntity().copy(page = page) }
            val endOfPaginationReached = movies.isEmpty()

            movieDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    movieDatabase.movieEntityDao().deleteAll()
                    movieDatabase.remoteKeyEntityDao().deleteAll()
                }

                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (endOfPaginationReached) null else page + 1
                val remoteKeys = movies.map {
                    RemoteKeyEntity(
                        movieID = it.id,
                        prevKey = prevKey,
                        currentPage = page,
                        nextKey = nextKey
                    )
                }

                movieDatabase.remoteKeyEntityDao().insertAll(remoteKeys)
                movieDatabase.movieEntityDao().insertOrUpdate(*movies.toTypedArray())
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, MovieEntity>): RemoteKeyEntity? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { movie ->
            movieDatabase.remoteKeyEntityDao().getRemoteKeyByMovieID(movie.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, MovieEntity>): RemoteKeyEntity? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { movie ->
            movieDatabase.remoteKeyEntityDao().getRemoteKeyByMovieID(movie.id)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, MovieEntity>): RemoteKeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                movieDatabase.remoteKeyEntityDao().getRemoteKeyByMovieID(id)
            }
        }
    }
}