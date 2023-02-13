package id.yuana.compose.movieapp.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import id.yuana.compose.movieapp.R

@Composable
fun ErrorRetryComponent(
    onRetryClick: () -> Unit,
    errorMessage: String? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = errorMessage
                ?: stringResource(id = R.string.text_oops_something_went_wrong)
        )
        Button(onClick = onRetryClick) {
            Text(text = stringResource(id = R.string.text_retry))
        }
    }
}