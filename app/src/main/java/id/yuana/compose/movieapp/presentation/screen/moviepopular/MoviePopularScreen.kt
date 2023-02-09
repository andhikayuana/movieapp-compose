@file:OptIn(ExperimentalMaterial3Api::class)

package id.yuana.compose.movieapp.presentation.screen.moviepopular

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import id.yuana.compose.movieapp.navigation.MovieRoutes
import id.yuana.compose.movieapp.presentation.ui.component.MovieItemCard
import kotlinx.coroutines.Dispatchers

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MoviePopularScreen(
    rootNavController: NavHostController,
    viewModel: MoviePopularViewModel = hiltViewModel()
) {
    val lazyGridState = rememberLazyGridState()
    val uiState by viewModel.uiState.collectAsState()
    val movies = uiState.movies.collectAsLazyPagingItems()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = viewModel.isLoading,
        onRefresh = {
            movies.refresh()
        }
    )


    LaunchedEffect(key1 = true) {
        viewModel.onEvent(MoviePopularEvent.FetchMoviePopular)
    }

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

        PullRefreshIndicator(viewModel.isLoading, pullRefreshState, Modifier.align(Alignment.TopCenter))

    }

}


inline fun <T : Any> LazyGridScope.items(
    items: LazyPagingItems<T>,
    noinline key: ((item: T) -> Any)? = null,
    noinline span: (LazyGridItemSpanScope.(item: T) -> GridItemSpan)? = null,
    noinline contentType: (item: T) -> Any? = { null },
    crossinline itemContent: @Composable LazyGridItemScope.(item: T) -> Unit
) = items(
    count = items.itemCount,
    key = if (key != null) { index: Int -> key(items[index]!!) } else null,
    span = if (span != null) {
        { span(items[it]!!) }
    } else null,
    contentType = { index: Int -> contentType(items[index]!!) }
) {
    itemContent(items[it]!!)
}
