@file:OptIn(ExperimentalMaterial3Api::class)

package id.yuana.compose.movieapp.presentation.screen.moviepopular

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import id.yuana.compose.movieapp.R
import id.yuana.compose.movieapp.core.items
import id.yuana.compose.movieapp.navigation.MovieRoutes
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

            MoviePopularError(movies.loadState)

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
                    MovieItemCard(
                        onClick = {
                            rootNavController.navigate(MovieRoutes.MovieDetail.routeFor(movie))
                        },
                        movie = movie
                    )
                }
            }

            PullRefreshIndicator(
                movies.loadState.refresh == LoadState.Loading,
                pullRefreshState,
                Modifier.align(Alignment.TopCenter)
            )

        }
    }


}

@Composable
fun MoviePopularError(loadState: CombinedLoadStates) {
    //first load
    when (val state = loadState.refresh) {
        is LoadState.Error -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = state.error.message
                        ?: stringResource(id = R.string.text_oops_something_went_wrong)
                )
            }
        }
        else -> Unit
    }
}

