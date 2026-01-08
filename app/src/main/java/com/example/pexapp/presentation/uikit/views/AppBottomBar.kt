package com.example.pexapp.presentation.uikit.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pexapp.R
import com.example.pexapp.presentation.navigation.AppScreen
import com.example.pexapp.presentation.uikit.theme.PexAppTheme
import com.example.pexapp.presentation.uikit.theme.appColor
import com.example.pexapp.presentation.uikit.theme.appSubtleColor
import com.example.pexapp.presentation.uikit.theme.bottomBarActiveIconColor
import com.example.pexapp.presentation.uikit.theme.bottomBarActiveScreenDividerHeight
import com.example.pexapp.presentation.uikit.theme.bottomBarActiveScreenDividerWidth
import com.example.pexapp.presentation.uikit.theme.bottomBarIconTopPadding

@Composable
fun AppBottomBar(
    modifier: Modifier = Modifier,
    onClick: (AppScreen) -> Unit = {},
    currentRoute: String?,
    items: List<AppScreen>
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        items.forEach { screen ->
            BottomBarItem(
                modifier = Modifier.weight(1f),
                appScreen = screen,
                isActive = currentRoute == screen.route,
                onClick = {
                    onClick(screen)
                }
            )
        }
    }
}

@Composable
private fun BottomBarItem(
    modifier: Modifier = Modifier,
    appScreen: AppScreen,
    isActive: Boolean,
    onClick: () -> Unit
) {
    val icon = if (isActive) appScreen.activeIcon else appScreen.notActiveIcon

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(visible = isActive) {
            HorizontalDivider(
                modifier = Modifier.width(bottomBarActiveScreenDividerWidth),
                thickness = bottomBarActiveScreenDividerHeight,
                color = if (isActive) bottomBarActiveIconColor else appColor
            )
        }

        Icon(
            modifier = Modifier
                .padding(bottomBarIconTopPadding)
                .clickable { onClick() },
            painter = painterResource(icon!!),
            contentDescription = stringResource(id = R.string.icon_string),
            tint = if (isActive) bottomBarActiveIconColor else appSubtleColor
        )
    }
}


@Composable
@Preview
private fun LightTheme() {
    PexAppTheme(
        darkTheme = false
    ) {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(appColor)
            ) {
                AppBottomBar(
                    modifier = Modifier
                        .padding(
                            top = 10.dp
                        )
                        .fillMaxWidth()
                        .padding(vertical = 5.dp, horizontal = 10.dp),
                    onClick = {},
                    currentRoute = "mainscreen",
                    items = listOf(
                        AppScreen.Home,
                        AppScreen.Favourite
                    )
                )

                AppBottomBar(
                    modifier = Modifier
                        .padding(
                            top = 10.dp
                        )
                        .fillMaxWidth()
                        .padding(vertical = 5.dp, horizontal = 10.dp),
                    onClick = {},
                    currentRoute = null,
                    items = listOf(
                        AppScreen.Home,
                        AppScreen.Favourite
                    )
                )

                AppBottomBar(
                    modifier = Modifier
                        .padding(
                            top = 10.dp
                        )
                        .fillMaxWidth()
                        .padding(vertical = 5.dp, horizontal = 10.dp),
                    onClick = {},
                    currentRoute = "favouritescreen",
                    items = listOf(
                        AppScreen.Home,
                        AppScreen.Favourite,
                        AppScreen.Settings
                    )
                )

                AppBottomBar(
                    modifier = Modifier
                        .padding(
                            top = 10.dp
                        )
                        .fillMaxWidth()
                        .padding(vertical = 5.dp, horizontal = 10.dp),
                    onClick = {},
                    currentRoute = "settingsscreen",
                    items = listOf(
                        AppScreen.Home,
                        AppScreen.Favourite,
                        AppScreen.Settings
                    )
                )
            }
        }
    }
}

@Composable
@Preview
private fun DarkTheme() {
    PexAppTheme(
        darkTheme = true
    ) {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(appColor)
            ) {
                AppBottomBar(
                    modifier = Modifier
                        .padding(
                            top = 10.dp
                        )
                        .fillMaxWidth()
                        .padding(vertical = 5.dp, horizontal = 10.dp),
                    onClick = {},
                    currentRoute = "mainscreen",
                    items = listOf(
                        AppScreen.Home,
                        AppScreen.Favourite
                    )
                )

                AppBottomBar(
                    modifier = Modifier
                        .padding(
                            top = 10.dp
                        )
                        .fillMaxWidth()
                        .padding(vertical = 5.dp, horizontal = 10.dp),
                    onClick = {},
                    currentRoute = null,
                    items = listOf(
                        AppScreen.Home,
                        AppScreen.Favourite
                    )
                )

                AppBottomBar(
                    modifier = Modifier
                        .padding(
                            top = 10.dp
                        )
                        .fillMaxWidth()
                        .padding(vertical = 5.dp, horizontal = 10.dp),
                    onClick = {},
                    currentRoute = "favouritescreen",
                    items = listOf(
                        AppScreen.Home,
                        AppScreen.Favourite
                    )
                )
            }
        }
    }
}