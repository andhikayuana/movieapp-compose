@file:OptIn(ExperimentalMaterial3Api::class)

package id.yuana.compose.movieapp.presentation.screen.moviepopular

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import id.yuana.compose.movieapp.domain.model.Movie
import id.yuana.compose.movieapp.navigation.MovieRoutes

@Composable
fun MoviePopularScreen(
    rootNavController: NavHostController,
    viewModel: MoviePopularViewModel = hiltViewModel()
) {
    val lazyGridState = rememberLazyGridState()
    val uiState by viewModel.uiState.collectAsState()
    val movies = uiState.movies.collectAsLazyPagingItems()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        state = lazyGridState
    ) {

        items(
            items = movies,
            key = { it.id }
        ) { movie ->
            MovieItemCard(
                rootNavController = rootNavController,
                movie = movie
            )
        }
    }
}

@Composable
fun MovieItemCard(
    rootNavController: NavHostController,
    movie: Movie
) {
    Card(
        onClick = {
            rootNavController.navigate(MovieRoutes.MovieDetail.routeFor(movie))
        }
    ) {
        Box(
            contentAlignment = Alignment.BottomCenter
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(movie.getPosterUrl())
                    .crossfade(true)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .build(),
                contentDescription = movie.title,
                modifier = Modifier.fillMaxWidth()
            )
        }


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
