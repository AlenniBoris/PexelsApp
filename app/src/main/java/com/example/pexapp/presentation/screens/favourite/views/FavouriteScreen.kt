package com.example.pexapp.presentation.screens.favourite.views

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.pexapp.R
import com.example.pexapp.domain.util.GsonUtil.toJson
import com.example.pexapp.presentation.navigation.AppScreen
import com.example.pexapp.presentation.screens.favourite.FavouriteScreenState
import com.example.pexapp.presentation.screens.favourite.FavouriteScreenViewModel
import com.example.pexapp.presentation.screens.favourite.IFavouriteScreenEvent
import com.example.pexapp.presentation.screens.favourite.IFavouriteScreenIntent
import com.example.pexapp.presentation.uikit.theme.PexAppTheme
import com.example.pexapp.presentation.uikit.theme.appColor
import com.example.pexapp.presentation.uikit.theme.appContentPadding
import com.example.pexapp.presentation.uikit.theme.favouriteScreenSectionTopPadding
import com.example.pexapp.presentation.uikit.theme.topBarInnerPadding
import com.example.pexapp.presentation.uikit.theme.topBarOuterPadding
import com.example.pexapp.presentation.uikit.views.AppEmptyScreen
import com.example.pexapp.presentation.uikit.views.AppPhotoSection
import com.example.pexapp.presentation.uikit.views.AppTopBar
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch

@Composable
fun FavouriteScreen(
    navController: NavHostController,
) {

    val viewModel: FavouriteScreenViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val event by remember { mutableStateOf(viewModel.event) }
    val proceedIntent by remember { mutableStateOf(viewModel::proceedIntent) }

    LaunchedEffect(event) {
        launch {
            event.filterIsInstance<IFavouriteScreenEvent.OpenPhoto>().collect { coming ->
                val encodedJson = Uri.encode(coming.photo.toJson())
                navController.navigate(AppScreen.Details.route + encodedJson)
            }
        }

        launch {
            event.filterIsInstance<IFavouriteScreenEvent.OpenHomeScreen>().collect {
                navController.navigate(AppScreen.Home.route)
            }
        }
    }

    FavouriteScreenUi(
        state = state,
        proceedIntent = proceedIntent
    )
}

@Composable
private fun FavouriteScreenUi(
    state: FavouriteScreenState,
    proceedIntent: (IFavouriteScreenIntent) -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize().background(appColor)
    ) {
        AppTopBar(
            modifier = Modifier
                .padding(topBarOuterPadding)
                .fillMaxWidth()
                .padding(topBarInnerPadding),
            text = stringResource(R.string.favourite_screen_top)
        )

        when {
            state.workingList.isEmpty() -> {
                AppEmptyScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(favouriteScreenSectionTopPadding)
                        .padding(appContentPadding),
                    text = stringResource(R.string.nothing_saved_text),
                    onExploreClicked = {
                        proceedIntent(
                            IFavouriteScreenIntent.OpenHomeScreen
                        )
                    }
                )
            }

            else -> {
                AppPhotoSection(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(favouriteScreenSectionTopPadding)
                        .padding(appContentPadding),
                    photos = state.workingList,
                    isSimple = true,
                    onPhotoClicked = { photo ->
                        proceedIntent(
                            IFavouriteScreenIntent.OpenPhoto(photo)
                        )
                    }
                )
            }
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
                FavouriteScreenUi(
                    state = FavouriteScreenState(),
                    proceedIntent = {}
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
                FavouriteScreenUi(
                    state = FavouriteScreenState(),
                    proceedIntent = {}
                )
            }
        }
    }
}