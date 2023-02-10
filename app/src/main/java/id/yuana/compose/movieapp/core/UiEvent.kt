package id.yuana.compose.movieapp.core

sealed class UiEvent {
    object PopBackStack : UiEvent()
    object ShowDialog : UiEvent()
    data class Loading(val visible: Boolean) : UiEvent()
    data class Navigate(val route: String, val clearBackStack: Boolean = false) : UiEvent()
    data class ShowSnackbar(val message: String, val action: String? = null) : UiEvent()
}