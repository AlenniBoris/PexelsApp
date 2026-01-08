package com.example.pexapp.presentation.uikit.theme

import androidx.compose.ui.graphics.Color
import com.example.pexapp.presentation.uikit.utils.currentThemeMode

val Red = Color(0xFFBB1020)
val Gray = Color(0xFFF3F5F9)
val GrayDark = Color(0xFF393939)

val DarkGray = Color(0xFF868686)
val LightGray = Color(0xFFB5B5B5)

val White = Color(0xFFFFFFFF)
val Black = Color(0xFF1E1E1E)

private val AppColorLight = Color(0xFFFFFFFF)
private val AppColorDark = Color(0xFF1E1E1E)
val appColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> AppColorLight
        true -> AppColorDark
    }

private val ClickableElementBackgroundLight = Color(0xFFF3F5F9)
private val ClickableElementBackgroundDark = Color(0xFF393939)
val clickableElementBackground
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> ClickableElementBackgroundLight
        true -> ClickableElementBackgroundDark
    }

private val BaseTextColorLight = Color(0xFF1E1E1E)
private val BaseTextColorDark = Color(0xFFFFFFFF)
val baseTextColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> BaseTextColorLight
        true -> BaseTextColorDark
    }

val featuredItemActiveTextColor = Color(0xFFFFFFFF)
val featuredItemActiveBackgroundColor = Color(0xFFBB1020)

val pictureTextBackgroundColor = Color(0xFF1E1E1E).copy(alpha = 0.4f)
val pictureTextColor = Color(0xFFFFFFFF)

val bottomBarActiveIconColor = Color(0xFFBB1020)

val supportButtonsBackgroundColor = Color(0xFFBB1020)
val extraTextColor = Color(0xFFBB1020)
val supportButtonsTextColor = Color(0xFFFFFFFF)

val appSubtleColor = Color(0xFFB5B5B5)
val progressIndicatorColor = Color(0xFFBB1020)

val historyItemActiveTextColor = Color(0xFFFFFFFF)
val historyItemActiveBackgroundColor = Color(0xFFBB1020)