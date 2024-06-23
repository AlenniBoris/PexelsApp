package com.example.pexapp.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pexapp.screens.DetailsScreen
import com.example.pexapp.screens.FavouriteScreen
import com.example.pexapp.screens.MainScreen
import com.example.pexapp.screens.SplashScreen

@Composable
fun NavigationGraph(
    navHostController: NavHostController,
    padding: PaddingValues
){
    NavHost(
        navController = navHostController,
        startDestination = Route.splashRoute.routeToScreen,
        modifier = Modifier.padding(padding)
    ){
        composable(Route.mainRoute.routeToScreen){
            MainScreen(navController = navHostController)
        }
        composable(Route.favouriteRoute.routeToScreen){
            FavouriteScreen(
                navHostController = navHostController
            )
        }
        composable(Route.detailsRoute.routeToScreen){ backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            DetailsScreen(
                id = arguments.getString("id"),
                navController = navHostController
            )
        }
        composable(Route.splashRoute.routeToScreen){
            SplashScreen(navController = navHostController)
        }
    }
}