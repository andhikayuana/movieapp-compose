@file:OptIn(ExperimentalMaterialApi::class)

package id.yuana.compose.movieapp.presentation.screen.moviefavorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
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

@Composable
fun MovieFavoriteScreen(
    rootNavController: NavHostController,
    viewModel: MovieFavoriteViewModel = hiltViewModel()
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