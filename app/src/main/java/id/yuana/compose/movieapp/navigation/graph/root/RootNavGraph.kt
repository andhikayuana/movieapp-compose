package id.yuana.compose.movieapp.navigation.graph.root

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import id.yuana.compose.movieapp.navigation.MovieRoutes
import id.yuana.compose.movieapp.navigation.graph.home.MovieAppHomeNavigation
import id.yuana.compose.movieapp.presentation.screen.moviedetail.MovieDetailScreen

@Composable
fun MovieAppRootNavigation() {
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
        MovieAppHomeNavigation(navController)
    }
    composable(
        route = MovieRoutes.MovieDetail.route
    ) {
        MovieDetailScreen()
    }
}