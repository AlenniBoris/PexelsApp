package com.example.pexapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pexapp.navigation.NavigationGraph
import com.example.pexapp.navigation.Screen
import com.example.pexapp.ui.theme.PexAppTheme
import com.example.pexapp.utils.appBottomBar

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PexAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainActivityShowFunction()
                }
            }
        }
    }
}

@Composable
fun MainActivityShowFunction(){
    val navController = rememberNavController()
    val screensForIcons = listOf(
        Screen.Main,
        Screen.Favourite
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen : String? = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = if (screensForIcons.map { it.route }.contains(currentScreen)) {
            appBottomBar(
                navController = navController,
                navBackStackEntry = navBackStackEntry,
                items = screensForIcons
            )
        } else{
            {}
        }
    ) {
        innerPadding -> NavigationGraph(navHostController = navController, padding = innerPadding)
    }

}