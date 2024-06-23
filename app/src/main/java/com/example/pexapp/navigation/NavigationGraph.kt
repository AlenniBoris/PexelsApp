package com.example.pexapp.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pexapp.screens.details.views.DetailsScreen
import com.example.pexapp.screens.favourite.views.FavouriteScreen
import com.example.pexapp.screens.main.views.MainScreen
@Composable
fun NavigationGraph(
    navHostController: NavHostController,
    padding: PaddingValues
){
    NavHost(
        navController = navHostController,
        startDestination = Route.MainRoute.routeToScreen,
        modifier = Modifier.padding(padding)
    ){
        composable(Route.MainRoute.routeToScreen){
            MainScreen(navController = navHostController)
        }
        composable(Route.FavouriteRoute.routeToScreen){
            FavouriteScreen(
                navHostController = navHostController
            )
        }
        composable(Route.DetailsRoute.routeToScreen){ backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            DetailsScreen(
                id = arguments.getString("id"),
                navController = navHostController
            )
        }
    }
}