@file:OptIn(ExperimentalMaterial3Api::class)

package id.yuana.compose.movieapp.presentation.screen.moviedetail

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import id.yuana.compose.movieapp.R
import id.yuana.compose.movieapp.domain.model.Movie
import id.yuana.compose.movieapp.domain.model.Video

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    rootNavController: NavHostController,
    movie: Movie?,
    viewModel: MovieDetailViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()
    val movieDetail = uiState.movie.collectAsState(initial = movie).value
    val movieVideos = uiState.videos.collectAsState(initial = emptyList()).value
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = Unit) {
        movie?.let {
            viewModel.fetchDetail(it)
        }
    }

    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .verticalScroll(scrollState),
        ) {
            Box(
                contentAlignment = Alignment.TopStart,
                modifier = Modifier
                    .height(270.dp)
                    .fillMaxWidth()
            ) {
                Box {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(movieDetail?.getBackdropUrl())
                            .crossfade(true)
                            .diskCachePolicy(CachePolicy.ENABLED)
                            .build(),
                        contentDescription = movieDetail?.title,
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.FillWidth
                    )
                }

                Box(modifier = Modifier.align(Alignment.BottomStart)) {
                    Card(
                        modifier = Modifier
                            .shadow(28.dp, clip = false)
                            .padding(start = 10.dp, top = 10.dp),
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(movieDetail?.getPosterUrl("w342"))
                                .crossfade(true)
                                .diskCachePolicy(CachePolicy.ENABLED)
                                .build(),
                            contentDescription = movieDetail?.title,
                            modifier = Modifier.height(200.dp)
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 20.dp, top = 30.dp)
                ) {
                    Row {
                        AssistChip(
                            onClick = { /* Do something! */ },
                            label = { Text(String.format("%.1f", movieDetail?.voteAverage)) },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Rounded.Star,
                                    contentDescription = stringResource(
                                        id = R.string.text_rating
                                    )
                                )
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        AssistChip(
                            onClick = { /*TODO*/ },
                            label = { Text(text = stringResource(id = R.string.text_favorite)) },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Rounded.FavoriteBorder,
                                    contentDescription = stringResource(id = R.string.text_favorite)
                                )
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }

            }
            Spacer(modifier = Modifier.height(8.dp))
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                Text(
                    text = "${movieDetail?.titleOriginal}",
                    style = MaterialTheme.typography.titleLarge
                )
                Row {
                    Text(
                        text = "${movieDetail?.getReleaseYear()}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${movieDetail?.getDuration()}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                LazyRow {
                    movieDetail?.genres?.let { genres ->
                        items(genres) { genre ->
                            ElevatedAssistChip(
                                onClick = { /*do something*/ },
                                label = { Text(text = genre.name) }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "${movieDetail?.overview}",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )

                VideoLazyRow(movieVideos)
            }
        }


    }
}

@Composable
private fun VideoLazyRow(movieVideos: List<Video>) {
    Spacer(modifier = Modifier.height(10.dp))
    Divider()
    Spacer(modifier = Modifier.height(10.dp))
    Text(
        text = stringResource(id = R.string.text_videos),
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Justify
    )
    LazyRow(
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(movieVideos) { video ->
            VideoItemCard(video)
        }
    }
}

@Composable
internal fun VideoItemCard(video: Video) {
    val context = LocalContext.current
    Card(
        onClick = {
            val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:${video.key}"))
            val webIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=${video.key}")
            )
            try {
                context.startActivity(appIntent)
            } catch (ex: ActivityNotFoundException) {
                context.startActivity(webIntent)
            }
        },
        modifier = Modifier.width(200.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(video.getYoutubeThumbnail())
                    .crossfade(true)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .build(),
                contentDescription = video.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentScale = ContentScale.Crop,
            )
            Icon(
                imageVector = Icons.Rounded.PlayArrow,
                contentDescription = stringResource(
                    id = R.string.text_play
                ),
                modifier = Modifier.size(48.dp),
                tint = Color.White
            )
        }
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = video.name,
                style = MaterialTheme.typography.titleSmall,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = video.type,
                style = MaterialTheme.typography.bodySmall,
            )
        }

    }
}