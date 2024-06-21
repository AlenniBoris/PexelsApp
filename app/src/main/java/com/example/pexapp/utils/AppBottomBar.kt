package com.example.pexapp.utils

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.example.pexapp.navigation.Screen
import com.example.pexapp.ui.theme.Red

@Composable
fun appBottomBar(
    navController: NavController,
    navBackStackEntry: NavBackStackEntry?,
    items: List<Screen>
) : @Composable () -> Unit = {
    val currentRoute = navBackStackEntry?.destination

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .height(64.dp)
    ) {
        items.forEach{screen ->
            BottomBarItem(screen = screen, navController = navController, currentDest = currentRoute)
        }
    }
}

@Composable
fun BottomBarItem(
    screen: Screen,
    navController: NavController,
    currentDest: NavDestination?
){
    val itemIsActive = currentDest?.hierarchy?.any { it.route == screen.route} == true
    val icon = if (itemIsActive) screen.activeIcon else screen.notActiveIcon

    val contentColor =
        if (itemIsActive) MaterialTheme.colorScheme.tertiary
        else MaterialTheme.colorScheme.secondary

    //64 - height of bar, 24 of icon
    Box(
        modifier = Modifier.size(64.dp),
        contentAlignment = Alignment.TopCenter
    ){
        Box(
            modifier = Modifier
                .height(64.dp)
                .clip(RoundedCornerShape(32.dp))
                .clickable {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = icon!!),
                contentDescription = "icon",
                tint = contentColor
            )
        }
        AnimatedVisibility(visible = itemIsActive) {
            Divider(
                modifier = Modifier.size(24.dp, 2.dp),
                color = contentColor
            )
        }
    }

}