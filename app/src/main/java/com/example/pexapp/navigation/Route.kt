package com.example.pexapp.navigation

sealed class Route(val routeToScreen: String) {
    object MainRoute: Route("mainscreen")
    object FavouriteRoute: Route("favouritescreen")
    object DetailsRoute: Route("detailsscreen/{id}")
}