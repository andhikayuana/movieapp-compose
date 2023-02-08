package id.yuana.compose.movieapp.core

import androidx.navigation.NavHostController


fun NavHostController.navigateAndReplaceStartRoute(route: String) {
    popBackStack(graph.startDestinationId, true)
    graph.setStartDestination(route)
    navigate(route)
}

typealias OnNavigate = (UiEvent.Navigate) -> Unit
typealias OnPopBackStack = () -> Unit