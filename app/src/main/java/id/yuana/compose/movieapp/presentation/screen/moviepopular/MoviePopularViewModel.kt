package id.yuana.compose.movieapp.presentation.screen.moviepopular

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.yuana.compose.movieapp.core.MovieAppViewModel
import id.yuana.compose.movieapp.domain.usecase.GetMoviePopularUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviePopularViewModel @Inject constructor(
    private val getMoviePopularUseCase: GetMoviePopularUseCase
) : MovieAppViewModel() {

    private val _uiState = MutableStateFlow(MoviePopularState())
    val uiState: StateFlow<MoviePopularState> = _uiState

    init {
        viewModelScope.launch {
            _uiState.value = MoviePopularState(
                movies = getMoviePopularUseCase.invoke().cachedIn(viewModelScope)
            )
        }
    }

}