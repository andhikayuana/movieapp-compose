package id.yuana.compose.movieapp.domain.model

data class Crew(
    val adult: Boolean,
    val gender: Int,
    val id: Int,
    val knownForDepartment: String,
    val name: String,
    val originalName: String,
    val popularity: Double,
    val profilePath: String?,
    val creditId: String,
    val department: String,
    val job: String
) {
    fun getProfileImage(size: String = "w500"): String = when (profilePath) {
        null -> "https://cdn.landesa.org/wp-content/uploads/default-user-image.png"
        else -> "https://image.tmdb.org/t/p/${size}${profilePath}"
    }
}