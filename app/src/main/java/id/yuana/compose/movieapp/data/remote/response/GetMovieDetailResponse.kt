package id.yuana.compose.movieapp.data.remote.response

import com.google.gson.annotations.SerializedName
import java.util.*

data class GetMovieDetailResponse(
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("belongs_to_collection") val belongsToCollection: BelongsToCollection? = BelongsToCollection(),
    @SerializedName("budget") val budget: Int,
    @SerializedName("genres") val genres: List<Genre> = listOf(),
    @SerializedName("homepage") val homepage: String,
    @SerializedName("id") val id: Int,
    @SerializedName("imdb_id") val imdbId: String,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("production_companies") val productionCompanies: List<ProductionCompanies> = listOf(),
    @SerializedName("production_countries") val productionCountries: List<ProductionCountries> = listOf(),
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("revenue") val revenue: Long,
    @SerializedName("runtime") val runtime: Int,
    @SerializedName("spoken_languages") val spokenLanguages: List<SpokenLanguages> = listOf(),
    @SerializedName("status") val status: String,
    @SerializedName("tagline") val tagline: String,
    @SerializedName("title") val title: String,
    @SerializedName("video") val video: Boolean,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int

) {
    data class BelongsToCollection(
        @SerializedName("id") val id: Int = -1,
        @SerializedName("name") val name: String = "",
        @SerializedName("poster_path") val posterPath: String = "",
        @SerializedName("backdrop_path") val backdropPath: String = ""

    )

    data class Genre(
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String

    )

    data class ProductionCompanies(
        @SerializedName("id") val id: Int,
        @SerializedName("logo_path") val logoPath: String,
        @SerializedName("name") val name: String,
        @SerializedName("origin_country") val originCountry: String

    )

    data class ProductionCountries(
        @SerializedName("iso_3166_1") val iso31661: String,
        @SerializedName("name") val name: String

    )

    data class SpokenLanguages(
        @SerializedName("english_name") val englishName: String,
        @SerializedName("iso_639_1") val iso6391: String,
        @SerializedName("name") val name: String

    )
}