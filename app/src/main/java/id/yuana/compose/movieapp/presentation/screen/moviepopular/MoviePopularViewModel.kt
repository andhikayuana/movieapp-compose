package id.yuana.compose.movieapp.presentation.screen.moviepopular

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.yuana.compose.movieapp.core.MovieAppViewModel
import id.yuana.compose.movieapp.domain.usecase.GetMoviePopularUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviePopularViewModel @Inject constructor(
    private val getMoviePopularUseCase: GetMoviePopularUseCase
) : MovieAppViewModel() {

    var uiState = MutableStateFlow(MoviePopularState())
        private set
    var isLoading by mutableStateOf(false)
        private set

    fun onEvent(event: MoviePopularEvent) {
        when (event) {
            MoviePopularEvent.FetchMoviePopular -> {
                viewModelScope.launch {
                    getMoviePopularUseCase
                        .invoke()
                        .cachedIn(viewModelScope)
                        .onStart {
                            isLoading = true
                        }
                        .onCompletion {
                            isLoading = false
                        }
                        .collectLatest { pagingDataMovie ->
                            uiState.update {
                                it.copy(movies = flowOf(pagingDataMovie))
                            }

                        }
                }
            }
        }
    }

}