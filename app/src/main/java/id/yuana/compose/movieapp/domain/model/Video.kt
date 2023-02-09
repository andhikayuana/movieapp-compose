package id.yuana.compose.movieapp.domain.model

data class Video(
    val id: String,
    val name: String,
    val type: String,
    val site: String,
    val key: String
) {
    /**
     * @see https://stackoverflow.com/questions/2068344/how-do-i-get-a-youtube-video-thumbnail-from-the-youtube-api
     */
    fun getYoutubeThumbnail(): String = "https://img.youtube.com/vi/${key}/0.jpg"
}