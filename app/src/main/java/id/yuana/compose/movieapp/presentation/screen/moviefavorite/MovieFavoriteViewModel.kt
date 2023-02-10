package id.yuana.compose.movieapp.presentation.screen.moviefavorite

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.yuana.compose.movieapp.core.MovieAppViewModel
import id.yuana.compose.movieapp.domain.usecase.GetMovieFavoriteUseCase
import javax.inject.Inject

@HiltViewModel
class MovieFavoriteViewModel @Inject constructor(
    private val getMovieFavoriteUseCase: GetMovieFavoriteUseCase
) : MovieAppViewModel() {

    val movies = getMovieFavoriteUseCase().cachedIn(viewModelScope)

}