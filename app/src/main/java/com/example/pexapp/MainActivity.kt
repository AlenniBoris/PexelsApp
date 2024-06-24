package com.example.pexapp

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pexapp.navigation.NavigationGraph
import com.example.pexapp.navigation.Screen
import com.example.pexapp.screens.main.MainScreenViewModel
import com.example.pexapp.uikit.theme.PexAppTheme
import com.example.pexapp.uikit.views.appBottomBar
import com.example.pexapp.utils.ExtraFunctions.hasInternetConnection
import com.google.accompanist.systemuicontroller.rememberSystemUiController

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()

        setContent {
            PexAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainActivityShowFunction(splashScreen, applicationContext)
                }
            }
        }
    }
}

@Composable
fun MainActivityShowFunction(
    splashScreen: SplashScreen,
    context: Context
){

    val mainScreenViewModel: MainScreenViewModel = hiltViewModel()
    val mainScreenState = mainScreenViewModel.screenState.collectAsStateWithLifecycle()

    splashScreen.apply {
        setKeepOnScreenCondition {
            if (!hasInternetConnection(context)){
                mainScreenState.value.photos.isNotEmpty()
            }else{
                mainScreenState.value.photos.isEmpty()
            }

        }
    }

    val navController = rememberNavController()
    val screensForIcons = listOf(
        Screen.Main,
        Screen.Favourite
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen : String? = navBackStackEntry?.destination?.route

    val systemUiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colorScheme.background == Color.White

    systemUiController.setSystemBarsColor(
        color = MaterialTheme.colorScheme.background,
        darkIcons = useDarkIcons
    )

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