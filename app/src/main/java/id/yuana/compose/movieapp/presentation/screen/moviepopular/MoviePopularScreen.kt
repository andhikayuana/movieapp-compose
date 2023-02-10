package id.yuana.compose.movieapp.presentation.screen.moviepopular

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import id.yuana.compose.movieapp.core.items
import id.yuana.compose.movieapp.navigation.MovieRoutes
import id.yuana.compose.movieapp.presentation.ui.component.MovieItemCard

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MoviePopularScreen(
    rootNavController: NavHostController,
    viewModel: MoviePopularViewModel = hiltViewModel()
) {
    val lazyGridState = rememberLazyGridState()
    val movies = viewModel.movies.collectAsLazyPagingItems()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = movies.loadState.refresh == LoadState.Loading,
        onRefresh = {
            movies.refresh()
        }
    )

    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
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