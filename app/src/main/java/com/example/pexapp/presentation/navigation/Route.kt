package com.example.pexapp.presentation.navigation

sealed class Route(val routeToScreen: String) {
    object MainRoute: Route("homescreen")
    object FavouriteRoute: Route("favouritescreen")
    object DetailsRoute: Route("detailsscreen/{id}")
}