package id.yuana.compose.movieapp.data.remote.response

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import retrofit2.HttpException

data class GeneralErrorResponse(
    @SerializedName("status_code")
    val statusCode: Int,
    @SerializedName("status_message")
    val statusMessage: String,
    val success: Boolean
)

inline fun <reified T> Throwable.parsedHttpError(): T? {
    if (this is HttpException) {
        val errorBody = response()?.errorBody()?.string() ?: return null
        return Gson().fromJson(errorBody, T::class.java)
    } else {
        return null
    }
}
