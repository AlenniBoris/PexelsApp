package com.example.pexapp.navigation

sealed class Route(val routeToScreen: String) {
    object mainRoute: Route("mainscreen")
    object favouriteRoute: Route("favouritescreen")
    object detailsRoute: Route("detailsscreen/{id}")
    object splashRoute: Route("splashscreen")
}