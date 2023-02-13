@file:OptIn(ExperimentalMaterial3Api::class)

package id.yuana.compose.movieapp.presentation.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import id.yuana.compose.movieapp.domain.model.Movie


@Composable
fun MovieItemCard(
    onClick: () -> Unit,
    movie: Movie
) {
    Card(
        onClick = onClick
    ) {
        Box(
            contentAlignment = Alignment.BottomCenter
        ) {
            AsyncImage(
                model = movie.getPosterUrl(),
                contentDescription = movie.title,
                modifier = Modifier.fillMaxWidth()
            )
        }


    }
}