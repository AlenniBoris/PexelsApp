package com.example.pexapp.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pexapp.presentation.screens.details.views.DetailsScreen
import com.example.pexapp.presentation.screens.favourite.views.FavouriteScreen
import com.example.pexapp.presentation.screens.home.views.HomeScreen

@Composable
fun NavigationGraph(
    navHostController: NavHostController,
    padding: PaddingValues
) {
    NavHost(
        navController = navHostController,
        startDestination = Route.MainRoute.routeToScreen,
        modifier = Modifier.padding(padding)
    ) {
        composable(Route.MainRoute.routeToScreen) {
            HomeScreen(
                navController = navHostController
            )
        }
        composable(Route.FavouriteRoute.routeToScreen) {
            FavouriteScreen(
                navHostController = navHostController
            )
        }
        composable(Route.DetailsRoute.routeToScreen) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            DetailsScreen(
                id = arguments.getString("id"),
                navController = navHostController
            )
        }
    }
}