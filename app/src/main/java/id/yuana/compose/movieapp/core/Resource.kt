package id.yuana.compose.movieapp.core

sealed class Resource<T>(
    val data: T?,
    val throwable: Throwable? = null,
) {
    object Loading : Resource<Unit>(null)
    class Success<T>(data: T?) : Resource<T>(data)
    class Error(throwable: Throwable?) : Resource<Unit>(
        data = null,
        throwable = throwable
    )
}