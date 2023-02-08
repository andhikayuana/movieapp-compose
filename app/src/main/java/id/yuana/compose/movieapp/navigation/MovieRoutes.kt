package id.yuana.compose.movieapp.navigation

sealed class MovieRoutes(
    val route: String,
) {
    object MovieHome : MovieRoutes("movie")
    object MoviePopular : MovieRoutes("movie/popular")
    object MovieFavorite : MovieRoutes("movie/favorite")
    object MovieDetail : MovieRoutes("movie/{id}")
}