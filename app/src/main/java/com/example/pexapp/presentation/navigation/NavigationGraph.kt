package com.example.pexapp.presentation.navigation

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pexapp.presentation.screens.details.views.DetailsScreen
import com.example.pexapp.presentation.screens.favourite.views.FavouriteScreen
import com.example.pexapp.presentation.screens.home.views.HomeScreen
import com.example.pexapp.presentation.screens.settings.views.SettingsScreen

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun NavigationGraph(
    navHostController: NavHostController,
    padding: PaddingValues
) {
    NavHost(
        navController = navHostController,
        startDestination = Route.MainRoute.routeToScreen
    ) {
        composable(Route.MainRoute.routeToScreen) {
            HomeScreen(
                navController = navHostController
            )
        }
        composable(Route.FavouriteRoute.routeToScreen) {
            FavouriteScreen(
                navController = navHostController
            )
        }
        composable(Route.DetailsRoute.routeToScreen) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val decodedJson = arguments.getString("photoJson")?.let { Uri.decode(it) }
            DetailsScreen(
                photoJson = decodedJson,
                navController = navHostController
            )
        }
        composable(Route.SettingsRoute.routeToScreen) {
            SettingsScreen(
                navController = navHostController
            )
        }
    }
}