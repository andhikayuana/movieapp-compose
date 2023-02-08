package id.yuana.compose.movieapp.presentation.screen.moviepopular

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.yuana.compose.movieapp.core.MovieAppViewModel
import id.yuana.compose.movieapp.domain.usecase.GetMoviePopularUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviePopularViewModel @Inject constructor(
    private val getMoviePopularUseCase: GetMoviePopularUseCase
) : MovieAppViewModel<MoviePopularAction>() {

    var state by mutableStateOf(MoviePopularState())
        private set

    init {
        getMoviePopular(DEFAULT_PAGE)
    }

    private fun getMoviePopular(page: Int, language: String = DEFAULT_LANGUAGE) {
        viewModelScope.launch {
            getMoviePopularUseCase.invoke(page, language)
                .catch {
                    handleError(it)
                }
                .collect {
                    state = state.copy(
                        currentPage = page,
                        movies = it
                    )
                }
        }
    }

    override fun onAction(action: MoviePopularAction) {
        when (action) {
            is MoviePopularAction.OnLoadNextPage -> getMoviePopular(action.nextPage)
        }

    }

    companion object {
        const val DEFAULT_PAGE = 1
        const val DEFAULT_LANGUAGE = "en"
    }

}