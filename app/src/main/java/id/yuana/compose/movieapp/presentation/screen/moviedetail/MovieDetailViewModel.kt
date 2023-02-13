package id.yuana.compose.movieapp.presentation.screen.moviedetail

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.yuana.compose.movieapp.core.MovieAppViewModel
import id.yuana.compose.movieapp.domain.model.Movie
import id.yuana.compose.movieapp.domain.usecase.AddRemoveMovieToFavoriteUseCase
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
    private val getMovieVideosUseCase: GetMovieVideosUseCase,
    private val addRemoveMovieToFavoriteUseCase: AddRemoveMovieToFavoriteUseCase
) : MovieAppViewModel() {

    private val _uiState = MutableStateFlow(MovieDetailState())
    val uiState: StateFlow<MovieDetailState> = _uiState

    fun fetchDetail(movie: Movie) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(movie = flowOf(movie))
            }
            _uiState.update {
                it.copy(movie = getMovieDetailUseCase(movie.id))
            }
            _uiState.update {
                it.copy(videos = getMovieVideosUseCase(movie.id))
            }
        }
    }

    fun addRemoveFavorite(movie: Movie) {
        viewModelScope.launch {
            addRemoveMovieToFavoriteUseCase(movie).collect { mov ->
                _uiState.update {
                    it.copy(movie = flowOf(movie.copy(favorite = mov.favorite)))
                }
            }

        }
    }
}