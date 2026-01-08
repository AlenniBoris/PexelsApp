package com.example.pexapp.presentation.screens.settings.views

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.pexapp.R
import com.example.pexapp.presentation.uikit.model.ClickableElement
import com.example.pexapp.presentation.uikit.theme.PexAppTheme
import com.example.pexapp.presentation.uikit.theme.appColor
import com.example.pexapp.presentation.uikit.theme.appContentPadding
import com.example.pexapp.presentation.uikit.theme.appRoundedShape
import com.example.pexapp.presentation.uikit.theme.appTextStyle
import com.example.pexapp.presentation.uikit.theme.baseTextColor
import com.example.pexapp.presentation.uikit.theme.extraTextSize
import com.example.pexapp.presentation.uikit.theme.settingsScreenSectionTopPadding
import com.example.pexapp.presentation.uikit.theme.settingsScreenSectionTripleTopPadding
import com.example.pexapp.presentation.uikit.theme.topBarInnerPadding
import com.example.pexapp.presentation.uikit.theme.topBarOuterPadding
import com.example.pexapp.presentation.uikit.utils.AppLanguage
import com.example.pexapp.presentation.uikit.utils.AppTheme
import com.example.pexapp.presentation.uikit.utils.currentLanguageMode
import com.example.pexapp.presentation.uikit.utils.currentThemeMode
import com.example.pexapp.presentation.uikit.utils.setLanguage
import com.example.pexapp.presentation.uikit.utils.setTheme
import com.example.pexapp.presentation.uikit.utils.toUiString
import com.example.pexapp.presentation.uikit.views.AppLazyButtonRow
import com.example.pexapp.presentation.uikit.views.AppTopBar

@Composable
fun SettingsScreen(
    navController: NavHostController
) {

    val currentTheme by currentThemeMode.collectAsStateWithLifecycle()
    val allThemes by remember { mutableStateOf(AppTheme.entries.toList()) }
    val isDarkTheme = isSystemInDarkTheme()
    val currentLanguage by currentLanguageMode.collectAsStateWithLifecycle()
    val allLanguages by remember { mutableStateOf(AppLanguage.entries.toList()) }

    SettingsScreenUi(
        currentTheme = currentTheme.theme,
        allThemes = allThemes,
        isDarkTheme = isDarkTheme,
        currentLanguage = currentLanguage.language,
        allLanguages = allLanguages
    )
}

@Composable
private fun SettingsScreenUi(
    currentTheme: AppTheme,
    allThemes: List<AppTheme>,
    isDarkTheme: Boolean,
    currentLanguage: AppLanguage,
    allLanguages: List<AppLanguage>
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(appColor)
    ) {

        AppTopBar(
            modifier = Modifier
                .padding(topBarOuterPadding)
                .fillMaxWidth()
                .padding(topBarInnerPadding),
            text = stringResource(R.string.settings_screen_top)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(appContentPadding)
        ) {

            AppLazyButtonRow(
                modifier = Modifier
                    .padding(settingsScreenSectionTopPadding)
                    .clip(appRoundedShape)
                    .fillMaxWidth(),
                currentElement = ClickableElement(
                    text = stringResource(currentTheme.toUiString()),
                    onClick = {}
                ),
                listOfElements = allThemes.map {
                    ClickableElement(
                        text = stringResource(it.toUiString()),
                        onClick = {
                            context.setTheme(
                                theme = it,
                                isThemeDark = isDarkTheme
                            )
                        }
                    )
                }
            )

            AppLazyButtonRow(
                modifier = Modifier
                    .padding(settingsScreenSectionTopPadding)
                    .clip(appRoundedShape)
                    .fillMaxWidth(),
                currentElement = ClickableElement(
                    text = stringResource(currentLanguage.toUiString()),
                    onClick = {}
                ),
                listOfElements = allLanguages.map {
                    ClickableElement(
                        text = stringResource(it.toUiString()),
                        onClick = {
                            context.setLanguage(
                                language = it
                            )
                        }
                    )
                }
            )

            Text(
                modifier = Modifier
                    .padding(settingsScreenSectionTripleTopPadding)
                    .fillMaxWidth(),
                text = stringResource(R.string.contribution_to_pexels),
                style = appTextStyle.copy(
                    fontSize = extraTextSize,
                    color = baseTextColor,
                    fontWeight = FontWeight.Bold
                )
            )
        }
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
                SettingsScreenUi(
                    currentTheme = AppTheme.LIGHT,
                    allThemes = AppTheme.entries.toList(),
                    isDarkTheme = false,
                    currentLanguage = AppLanguage.English,
                    allLanguages = AppLanguage.entries.toList()
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
                SettingsScreenUi(
                    currentTheme = AppTheme.LIGHT,
                    allThemes = AppTheme.entries.toList(),
                    isDarkTheme = false,
                    currentLanguage = AppLanguage.English,
                    allLanguages = AppLanguage.entries.toList()
                )
            }
        }
    }
}