package id.yuana.compose.movieapp.presentation.screen.moviepopular

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import id.yuana.compose.movieapp.navigation.MovieRoutes

@Composable
fun MoviePopularScreen(navController: NavHostController) {
    Column() {
        Text(text = "Popular")
        Button(onClick = {
            navController.navigate(MovieRoutes.MovieDetail.route.replace("{id}", "123"))
        }) {
            Text(text = "Goto Detail")
        }
    }
}