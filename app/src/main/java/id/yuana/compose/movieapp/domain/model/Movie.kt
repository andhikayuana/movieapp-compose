package id.yuana.compose.movieapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*
import kotlin.math.floor

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
    val genres: List<Genre> = emptyList(),
    val runtime: Int,
    val tagline: String,
    val favorite: Boolean = false
) : Parcelable {
    fun getPosterUrl(size: String = "w500"): String =
        "https://image.tmdb.org/t/p/${size}${posterPath}"

    fun getBackdropUrl(size: String = "w500"): String =
        "https://image.tmdb.org/t/p/${size}${backdropPath}"

    fun getDuration(): String {
        val day = floor((runtime / 1440).toDouble())
        val hour = floor((runtime - day * 1440) / 60)
        val min = runtime - day * 1440 - hour * 60
        return "${hour.toInt()}h ${min.toInt()}m"
    }

    fun getReleaseYear(): String = Calendar.getInstance().apply {
        time = releaseDate
    }[Calendar.YEAR].toString()

}
