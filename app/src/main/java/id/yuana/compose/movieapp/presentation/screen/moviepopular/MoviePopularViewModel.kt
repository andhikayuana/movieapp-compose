package id.yuana.compose.movieapp.presentation.screen.moviepopular

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.yuana.compose.movieapp.core.MovieAppViewModel
import id.yuana.compose.movieapp.domain.usecase.GetMoviePopularUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class MoviePopularViewModel @Inject constructor(
    private val getMoviePopularUseCase: GetMoviePopularUseCase
) : MovieAppViewModel() {

    val movies = getMoviePopularUseCase().cachedIn(viewModelScope)

}