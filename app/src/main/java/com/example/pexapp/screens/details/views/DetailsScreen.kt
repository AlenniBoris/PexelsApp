package com.example.pexapp.screens.details.views

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.pexapp.R
import com.example.pexapp.navigation.Route
import com.example.pexapp.screens.EmptyScreen
import com.example.pexapp.screens.details.DetailsScreenViewModel
import com.example.pexapp.uikit.views.AppLoadingProgressBar
import com.example.pexapp.uikit.views.AppTopBar

@Composable
fun DetailsScreen(
    id: String?,
    navController: NavHostController,
    detailsScreenViewModel: DetailsScreenViewModel = hiltViewModel()
){

    val context = LocalContext.current

    val screenState by detailsScreenViewModel.screenState.collectAsStateWithLifecycle()

    val prevRoute = navController.previousBackStackEntry?.destination?.route

    when(prevRoute){
        Route.MainRoute.routeToScreen ->
            detailsScreenViewModel.getPhotoFromPexelsById(id?.toInt())
        Route.FavouriteRoute.routeToScreen ->
            detailsScreenViewModel.getPhotoFromFavouritesDatabaseById(id?.toInt())
    }

    val window = (context as Activity).window
    window.statusBarColor = MaterialTheme.colorScheme.background.toArgb()

    val scrollState = rememberScrollState()

    AppLoadingProgressBar(isLoading = (screenState.currentPhoto == null))

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                hasButton = true,
                text = screenState.currentPhoto?.photographer.toString(),
                textVisible = true,
                navController = navController
            )
        }
    ) {
        val pd = it
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(pd)
                .padding(horizontal = 24.dp)
                .verticalScroll(scrollState)
        ) {
            if (screenState.currentPhoto != null){
                DetailsImage(currentPhoto = screenState.currentPhoto)
                DetailsBottomBarButtons(
                    context = context,
                    detailsScreenViewModel = detailsScreenViewModel,
                    currentPhoto = screenState.currentPhoto!!,
                    currentPhotoIsInFavourite = screenState.photoIsFavourite,
                    id = id?.toInt()!!,
                    navController = navController
                )
            }else{
                EmptyScreen(
                    isMainScreen = false,
                    onExploreClicked = {
                        navController.popBackStack()
                    },
                    text = stringResource(id = R.string.image_not_found)
                )
            }
        }
    }
}



