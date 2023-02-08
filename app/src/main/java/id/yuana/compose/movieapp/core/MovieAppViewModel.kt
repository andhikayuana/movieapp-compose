package id.yuana.compose.movieapp.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class MovieAppViewModel<A : Action> : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    protected fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    protected fun handleError(t: Throwable) =
        sendUiEvent(UiEvent.ShowSnackbar(t.message ?: "Unknown Error"))

    abstract fun onAction(action: A)
}