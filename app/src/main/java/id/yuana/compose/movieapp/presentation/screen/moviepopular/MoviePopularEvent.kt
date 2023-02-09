package id.yuana.compose.movieapp.presentation.screen.moviepopular

sealed interface MoviePopularEvent {
    object FetchMoviePopular : MoviePopularEvent
}