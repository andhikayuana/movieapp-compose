package id.yuana.compose.movieapp.presentation.screen.moviepopular

import id.yuana.compose.movieapp.core.Action

sealed interface MoviePopularAction : Action {
    data class OnLoadNextPage(val nextPage: Int) : MoviePopularAction
}