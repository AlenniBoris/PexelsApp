package com.example.pexapp.presentation.uikit.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pexapp.presentation.uikit.utils.currentLanguageMode
import com.example.pexapp.presentation.uikit.utils.currentThemeMode
import com.example.pexapp.presentation.uikit.utils.getLastLanguageAndApply
import com.example.pexapp.presentation.uikit.utils.getLastThemeAndApply

private val DarkColorScheme = darkColorScheme(
    background = Black,
    onBackground = White,
    primary = GrayDark,
    onPrimary = White,
    secondary = LightGray,
    tertiary = Red,
)

private val LightColorScheme = lightColorScheme(
    background = White,
    onBackground = Black,
    primary = Gray,
    onPrimary = Black,
    secondary = DarkGray,
    tertiary = Red,
)

@Composable
fun PexAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {

    val context = LocalContext.current
    val themeModeInit = remember { context.getLastThemeAndApply(isSystemDarkMode = darkTheme) }
    val langModeInit = remember { context.getLastLanguageAndApply() }

    val colorScheme by remember(
        key1 = currentThemeMode.collectAsStateWithLifecycle().value.isThemeDark,
        key2 = currentLanguageMode.collectAsStateWithLifecycle().value.language
    ) {
        mutableStateOf(LightColorScheme.copy())
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.navigationBarColor = appColor.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}