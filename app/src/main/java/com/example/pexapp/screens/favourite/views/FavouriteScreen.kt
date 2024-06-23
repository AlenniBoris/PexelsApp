package com.example.pexapp.screens.favourite.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.pexapp.R
import com.example.pexapp.navigation.Route
import com.example.pexapp.screens.EmptyScreen
import com.example.pexapp.screens.favourite.FavouriteScreenViewModel
import com.example.pexapp.uikit.views.AppTopBar

@Composable
fun FavouriteScreen(
    navHostController: NavHostController,
    favouriteScreenViewModel: FavouriteScreenViewModel = hiltViewModel()
){

    LaunchedEffect(Unit) {
        favouriteScreenViewModel.getFavouritePhotosInit()
    }

    val state by favouriteScreenViewModel.screenState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                hasButton = false,
                text = stringResource(id = R.string.bookmarks_header),
                textVisible = true,
                navController = navHostController
            )
        }
    ) {
        val pd = it

        Column(
            modifier = Modifier
                .fillMaxSize()
        ){

            Divider(modifier = Modifier.height(30.dp), color = MaterialTheme.colorScheme.background)

            if (state.favouritePhotos.isNotEmpty() && !state.isNoFavourite){
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 40.dp)
                ) {
                    items(state.favouritePhotos){favouritePhoto ->
                        PhotoCards(
                            favouritePhoto.id,
                            favouritePhoto.src.medium,
                            favouritePhoto.photographer,
                            navHostController
                        )
                    }
                }
            }else if(state.isNoFavourite) {
                EmptyScreen(
                    onExploreClicked = {
                        navHostController.navigate(Route.MainRoute.routeToScreen) {
                            popUpTo(navHostController.graph.startDestinationId) {
                                inclusive = true
                            }
                        }
                    },
                    text = stringResource(id = R.string.bookmarks_empty_text),
                    isMainScreen = false
                )
            }
        }
    }
}


