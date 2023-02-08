package id.yuana.compose.movieapp.navigation.graph.home

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import id.yuana.compose.movieapp.R
import id.yuana.compose.movieapp.navigation.MovieRoutes
import id.yuana.compose.movieapp.presentation.screen.moviefavorite.MovieFavoriteScreen
import id.yuana.compose.movieapp.presentation.screen.moviepopular.MoviePopularScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieHomeNavigation(
    rootNavController: NavHostController
) {
    val navController = rememberNavController()

    val bottomNavigationItems = listOf(
        BottomNavItem(
            route = MovieRoutes.MoviePopular.route,
            resId = R.string.text_popular,
            icon = Icons.Rounded.PlayArrow
        ),
        BottomNavItem(
            route = MovieRoutes.MovieFavorite.route,
            resId = R.string.text_favorite,
            icon = Icons.Rounded.Favorite
        ),
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(id = R.string.app_name)) },
            )
        },
        bottomBar = {
            MovieAppBottomBar(
                bottomNavItems = bottomNavigationItems,
                navController = navController,
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = MovieRoutes.MoviePopular.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            HomeNavGraph(navController, rootNavController)
        }
    }

}


@Composable
internal fun MovieAppBottomBar(
    bottomNavItems: List<BottomNavItem>,
    navController: NavHostController
) {
    var selectedItem by remember { mutableStateOf(0) }

    NavigationBar {
        bottomNavItems.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = stringResource(id = item.resId)) },
                label = { Text(stringResource(id = item.resId)) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navController.navigate(item.route)
                }
            )
        }
    }
}


internal data class BottomNavItem(
    val route: String,
    @StringRes val resId: Int,
    val icon: ImageVector
)

fun NavGraphBuilder.HomeNavGraph(
    navController: NavHostController,
    rootNavController: NavHostController
) {
    composable(route = MovieRoutes.MoviePopular.route) {
        MoviePopularScreen(rootNavController = rootNavController)
    }
    composable(route = MovieRoutes.MovieFavorite.route) {
        MovieFavoriteScreen()
    }
}