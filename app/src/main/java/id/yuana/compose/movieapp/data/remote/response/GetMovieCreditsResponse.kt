package id.yuana.compose.movieapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetMovieCreditsResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("cast") val cast: List<Cast> = listOf(),
    @SerializedName("crew") val crew: List<Crew> = listOf()
) {
    data class Cast(
        @SerializedName("adult") val adult: Boolean,
        @SerializedName("gender") val gender: Int,
        @SerializedName("id") val id: Int,
        @SerializedName("known_for_department") val knownForDepartment: String,
        @SerializedName("name") val name: String,
        @SerializedName("original_name") val originalName: String,
        @SerializedName("popularity") val popularity: Double,
        @SerializedName("profile_path") val profilePath: String?,
        @SerializedName("cast_id") val castId: Int,
        @SerializedName("character") val character: String,
        @SerializedName("credit_id") val creditId: String,
        @SerializedName("order") val order: Int

    )

    data class Crew(
        @SerializedName("adult") val adult: Boolean,
        @SerializedName("gender") val gender: Int,
        @SerializedName("id") val id: Int,
        @SerializedName("known_for_department") val knownForDepartment: String,
        @SerializedName("name") val name: String,
        @SerializedName("original_name") val originalName: String,
        @SerializedName("popularity") val popularity: Double,
        @SerializedName("profile_path") val profilePath: String,
        @SerializedName("credit_id") val creditId: String,
        @SerializedName("department") val department: String,
        @SerializedName("job") val job: String
    )
}
