package com.example.pexapp.presentation.uikit.utils

import android.content.Context
import com.example.pexapp.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


enum class AppTheme {
    LIGHT,
    DARK,
    SYSTEM
}

fun AppTheme.toUiString(): Int = when (this) {
    AppTheme.LIGHT -> R.string.light_theme_mode_text
    AppTheme.DARK -> R.string.dark_theme_mode_text
    AppTheme.SYSTEM -> R.string.system_theme_mode_text
}

sealed class ThemeMode(
    val isThemeDark: Boolean,
    val theme: AppTheme
) {
    class Light() :
        ThemeMode(isThemeDark = false, theme = AppTheme.LIGHT)

    class Dark() :
        ThemeMode(isThemeDark = true, theme = AppTheme.DARK)

    class System(isThemeDark: Boolean) :
        ThemeMode(isThemeDark = isThemeDark, theme = AppTheme.SYSTEM)
}

private val _currentThemeMode = MutableStateFlow<ThemeMode>(ThemeMode.Light())
val currentThemeMode = _currentThemeMode.asStateFlow()

private val PREFERENCIES_NAME = "LAST_THEME"
private val THEME_STRING_NAME = "THEME"

private fun AppTheme.update(isThemeDark: Boolean) {
    _currentThemeMode.update {
        when (this) {
            AppTheme.LIGHT -> ThemeMode.Light()
            AppTheme.DARK -> ThemeMode.Dark()
            AppTheme.SYSTEM -> ThemeMode.System(isThemeDark = isThemeDark)
        }
    }
}

fun Context.setTheme(
    theme: AppTheme,
    isThemeDark: Boolean
) {
    applicationContext.getSharedPreferences(PREFERENCIES_NAME, Context.MODE_PRIVATE)
        .edit()
        .putString(THEME_STRING_NAME, theme.name)
        .apply()

    theme.update(isThemeDark = isThemeDark)
}


fun Context.getLastThemeAndApply(
    isSystemDarkMode: Boolean
): AppTheme {
    val theme = applicationContext.getSharedPreferences(PREFERENCIES_NAME, Context.MODE_PRIVATE)
        .getString(THEME_STRING_NAME, null)?.let { lastTheme ->
            AppTheme.entries.find { it.name == lastTheme }
        } ?: AppTheme.SYSTEM
    theme.update(isThemeDark = isSystemDarkMode)
    return _currentThemeMode.value.theme
}