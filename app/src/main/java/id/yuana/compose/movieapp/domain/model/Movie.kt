package id.yuana.compose.movieapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val titleOriginal: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val backdropPath: String,
    val voteAverage: Double,
    val voteCount: Int,
    val releaseDate: Date,
    val genres: List<Genre>,
    val runtime: Int,
    val tagline: String
) : Parcelable {
    fun getPosterUrl(size: String = "w500"): String =
        "https://image.tmdb.org/t/p/${size}/${posterPath}"

    fun getBackdropUrl(size: String = "w500"): String =
        "https://image.tmdb.org/t/p/${size}/${backdropPath}"

}
