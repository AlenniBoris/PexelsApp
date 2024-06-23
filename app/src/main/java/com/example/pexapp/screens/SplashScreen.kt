package com.example.pexapp.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.pexapp.R
import com.example.pexapp.navigation.Route
import com.example.pexapp.ui.theme.Red
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController){

    var startAnimation by remember{ mutableStateOf(false) }
    val alphaAnimation = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween( durationMillis = 5000 ),
        label = "alpha animation"
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(5000)
        navController.navigate(Route.mainRoute.routeToScreen){
            popUpTo(Route.splashRoute.routeToScreen) { inclusive = true }
        }
    }

    Splash(alpha = alphaAnimation.value)
}

@Composable
fun Splash(alpha: Float){
    Box(
        modifier = Modifier.background(Red).fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier.size(120.dp).alpha(alpha = alpha),
            contentAlignment = Alignment.BottomEnd
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.app_splash_logo),
                contentDescription = "Logo Icon",
            )
        }
    }
}