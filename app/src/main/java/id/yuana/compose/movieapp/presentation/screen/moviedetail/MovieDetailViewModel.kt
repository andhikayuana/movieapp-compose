package id.yuana.compose.movieapp.presentation.screen.moviedetail

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.yuana.compose.movieapp.core.MovieAppViewModel
import id.yuana.compose.movieapp.domain.model.Movie
import id.yuana.compose.movieapp.domain.usecase.GetMovieDetailUseCase
import id.yuana.compose.movieapp.domain.usecase.GetMovieVideosUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val getMovieVideosUseCase: GetMovieVideosUseCase
) : MovieAppViewModel() {

    private val _uiState = MutableStateFlow(MovieDetailState())
    val uiState: StateFlow<MovieDetailState> = _uiState

    fun fetchDetail(movie: Movie) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(movie = flowOf(movie))
            }
            _uiState.update {
                it.copy(movie = getMovieDetailUseCase.invoke(movie.id))
            }
            _uiState.update {
                it.copy(videos = getMovieVideosUseCase.invoke(movie.id))
            }
        }
    }
}