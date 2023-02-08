package id.yuana.compose.movieapp.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import id.yuana.compose.movieapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    onPopBackStack: () -> Unit
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = stringResource(id = R.string.text_back),
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        onPopBackStack()
                    }
            )
        },
        modifier = Modifier.background(Color.Transparent)
    )
}