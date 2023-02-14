@file:OptIn(ExperimentalMaterial3Api::class)

package id.yuana.compose.movieapp.presentation.screen.moviepopular

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import id.yuana.compose.movieapp.core.PagingPlaceholderKey
import id.yuana.compose.movieapp.core.items
import id.yuana.compose.movieapp.domain.model.Movie
import id.yuana.compose.movieapp.navigation.MovieRoutes
import id.yuana.compose.movieapp.presentation.ui.component.ErrorRetryComponent
import id.yuana.compose.movieapp.presentation.ui.component.MovieItemCard

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MoviePopularScreen(
    rootNavController: NavHostController,
    viewModel: MoviePopularViewModel = hiltViewModel()
) {
    val cellCount = 2
    val lazyGridState = rememberLazyGridState()
    val movies = viewModel.movies.collectAsLazyPagingItems()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = movies.loadState.refresh == LoadState.Loading,
        onRefresh = {
            movies.refresh()
        }
    )

    Column {

        LazyRow(contentPadding = PaddingValues(horizontal = 14.dp)) {
            listOf(
                "Popular",
                "Latest",
                "Now Playing",
                "Top Rated",
                "Upcoming"
            ).forEach {
                item {
                    ElevatedFilterChip(
                        selected = "Popular" == it,
                        onClick = { },
                        label = { Text(text = it) }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }

        Box(
            modifier = Modifier
                .pullRefresh(pullRefreshState)
                .zIndex(-1f)
        ) {

            LazyVerticalGrid(
                columns = GridCells.Fixed(cellCount),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                state = lazyGridState,
                modifier = Modifier.fillMaxSize()
            ) {
                items(
                    items = movies,
                    key = { it.id }
                ) { movie ->
                    when (movie) {
                        null -> Unit
                        else -> {
                            MovieItemCard(
                                onClick = {
                                    rootNavController.navigate(MovieRoutes.MovieDetail.routeFor(movie))
                                },
                                movie = movie
                            )
                        }
                    }


                }

                when (val state = movies.loadState.append) {
                    is LoadState.Error -> {
                        item(span = { GridItemSpan(cellCount) }) {
                            ErrorRetryComponent(
                                errorMessage = state.error.message,
                                onRetryClick = {
                                    movies.retry()
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp)
                            )
                        }
                    }
                    LoadState.Loading -> {
                        item(span = { GridItemSpan(cellCount) }) {
                            LinearProgressIndicator(
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                    else -> Unit
                }


            }

            MoviePopularErrorFirstLoad(movies)

            PullRefreshIndicator(
                movies.loadState.refresh == LoadState.Loading,
                pullRefreshState,
                Modifier.align(Alignment.TopCenter)
            )

        }
    }


}


@Composable
fun MoviePopularErrorFirstLoad(
    movies: LazyPagingItems<Movie>
) {
    //first load
    when (val state = movies.loadState.refresh) {
        is LoadState.Error -> {
            ErrorRetryComponent(
                errorMessage = state.error.message,
                onRetryClick = {
                    movies.retry()
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            )
        }
        else -> Unit
    }
}

