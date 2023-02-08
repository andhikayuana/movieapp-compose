package id.yuana.compose.movieapp.navigation.graph.root

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import id.yuana.compose.movieapp.domain.model.Movie
import id.yuana.compose.movieapp.navigation.MovieRoutes
import id.yuana.compose.movieapp.navigation.graph.home.MovieHomeNavigation
import id.yuana.compose.movieapp.presentation.screen.moviedetail.MovieDetailScreen

@Composable
fun MovieRootNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = MovieRoutes.MovieHome.route,
    ) {
        RootNavGraph(navController)
    }
}

fun NavGraphBuilder.RootNavGraph(
    navController: NavHostController
) {
    composable(route = MovieRoutes.MovieHome.route) {
        MovieHomeNavigation(navController)
    }
    composable(
        route = MovieRoutes.MovieDetail.route,
        arguments = MovieRoutes.MovieDetail.args
    ) {
        val movie: Movie? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            it.arguments?.getParcelable("movie", Movie::class.java)
        } else {
            it.arguments?.getParcelable("movie")
        }
        MovieDetailScreen(
            rootNavController = navController,
            movie = movie
        )
    }
}