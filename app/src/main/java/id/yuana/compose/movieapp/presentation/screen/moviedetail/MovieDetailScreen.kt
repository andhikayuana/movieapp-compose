package id.yuana.compose.movieapp.presentation.screen.moviedetail

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import id.yuana.compose.movieapp.R
import id.yuana.compose.movieapp.domain.model.Movie

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    rootNavController: NavHostController,
    movie: Movie?
) {
    Scaffold() {
        Column(
            modifier = Modifier.padding(it),
        ) {
            //banner
            Box(
                contentAlignment = Alignment.TopStart,
                modifier = Modifier
                    .height(270.dp)
                    .fillMaxWidth()
            ) {
                Box {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(movie?.getBackdropUrl())
                            .crossfade(true)
                            .diskCachePolicy(CachePolicy.ENABLED)
                            .build(),
                        contentDescription = movie?.title,
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
                                .data(movie?.getPosterUrl("w342"))
                                .crossfade(true)
                                .diskCachePolicy(CachePolicy.ENABLED)
                                .build(),
                            contentDescription = movie?.title,
                            modifier = Modifier.height(200.dp)
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(start = 20.dp, top = 30.dp)
                ) {
                    Button(onClick = {
                        //todo
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.PlayArrow,
                            contentDescription = stringResource(
                                id = R.string.text_play
                            ),
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                        Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                        Text(text = stringResource(id = R.string.text_trailer))

                    }
                }

            }
            Spacer(modifier = Modifier.height(10.dp))
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                Text(
                    text = "${movie?.title}",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "${movie?.overview}",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )
            }
        }


    }
}