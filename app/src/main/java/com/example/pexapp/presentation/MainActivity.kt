package com.example.pexapp.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pexapp.presentation.navigation.AppScreen
import com.example.pexapp.presentation.navigation.NavigationGraph
import com.example.pexapp.presentation.uikit.theme.PexAppTheme
import com.example.pexapp.presentation.uikit.theme.appColor
import com.example.pexapp.presentation.uikit.theme.bottomBarInnerPadding
import com.example.pexapp.presentation.uikit.utils.NetworkMonitorUtil
import com.example.pexapp.presentation.uikit.views.AppBottomBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        NetworkMonitorUtil.init(this)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !NetworkMonitorUtil.isConnected.value
            }
        }

        setContent {
            PexAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    MainActivityShowFunction()
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
private fun MainActivityShowFunction() {
    val navController = rememberNavController()
    val screensForIcons = listOf(
        AppScreen.Home,
        AppScreen.Favourite,
        AppScreen.Settings
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen: String? = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (screensForIcons.map { it.route }.contains(currentScreen)) {
                AppBottomBar(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .fillMaxWidth()
                        .background(appColor)
                        .padding(bottomBarInnerPadding),
                    currentRoute = navBackStackEntry?.destination?.route,
                    items = screensForIcons,
                    onClick = { screen ->
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavigationGraph(
            navHostController = navController,
            padding = innerPadding
        )
    }
}
