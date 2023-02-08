package id.yuana.compose.movieapp.navigation

import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.gson.Gson
import id.yuana.compose.movieapp.domain.model.Movie

sealed class MovieRoutes(
    val route: String,
    val args: List<NamedNavArgument> = emptyList()
) {
    object MovieHome : MovieRoutes("movie")
    object MoviePopular : MovieRoutes("movie/popular")
    object MovieFavorite : MovieRoutes("movie/favorite")
    object MovieDetail : MovieRoutes(
        route = "movie/{id}?movie={movie}",
        args = listOf(
            navArgument("id") {
                type = NavType.IntType
            },
            navArgument("movie") {
                type = MovieArgumentType()
            }
        )
    ) {
        private val gson by lazy { Gson() }

        fun routeFor(movie: Movie): String {
            return "movie/${movie.id}?movie=${Uri.encode(gson.toJson(movie))}"
        }

        class MovieArgumentType : NavType<Movie>(isNullableAllowed = true) {
            override fun get(bundle: Bundle, key: String): Movie? {
                return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    bundle.getParcelable(key, Movie::class.java)
                } else {
                    bundle.getParcelable(key)
                }
            }

            override fun parseValue(value: String): Movie {
                return gson.fromJson(value, Movie::class.java)
            }

            override fun put(bundle: Bundle, key: String, value: Movie) {
                bundle.putParcelable(key, value)
            }

        }
    }
}