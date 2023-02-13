package id.yuana.compose.movieapp.domain.model

data class Cast(
    val adult: Boolean,
    val gender: Int,
    val id: Int,
    val knownForDepartment: String,
    val name: String,
    val originalName: String,
    val popularity: Double,
    val profilePath: String?,
    val castId: Int,
    val character: String,
    val creditId: String,
    val order: Int
) {
    fun getProfileImage(size: String = "w500"): String = when (profilePath) {
        null -> "https://cdn.landesa.org/wp-content/uploads/default-user-image.png"
        else -> "https://image.tmdb.org/t/p/${size}/${profilePath}"
    }

}
