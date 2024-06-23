package com.example.pexapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.pexapp.R
import com.example.pexapp.navigation.Route
import com.example.pexapp.navigation.Screen
import com.example.pexapp.ui.theme.White
import com.example.pexapp.utils.AppTopBar
import com.example.pexapp.viewmodel.FavouriteScreenViewModel

@Composable
fun FavouriteScreen(
    navHostController: NavHostController,
    favouriteScreenViewModel: FavouriteScreenViewModel = hiltViewModel()
){

    LaunchedEffect(Unit) {
        favouriteScreenViewModel.getFavouritePhotosInit()
    }

    val favouritePhotoList by favouriteScreenViewModel.favouritePhotos.collectAsStateWithLifecycle()
    val isNoFavourite by favouriteScreenViewModel.isNoFavourite.collectAsStateWithLifecycle()


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                hasButton = false,
                text = "Bookmarks",
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

            if (favouritePhotoList.isNotEmpty() && !isNoFavourite){
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    modifier = Modifier
                        .padding(horizontal = 16.dp).padding(top = 40.dp)
                ) {
                    items(favouritePhotoList){favouritePhoto ->
                        PhotoCards(
                            favouritePhoto.id,
                            favouritePhoto.src.medium,
                            favouritePhoto.photographer,
                            navHostController
                        )
                    }
                }
            }else if(isNoFavourite) {
                EmptyScreen(
                    onExploreClicked = {
                        navHostController.navigate(Route.mainRoute.routeToScreen) {
                            popUpTo(navHostController.graph.startDestinationId) {
                                inclusive = true
                            }
                        }
                    },
                    text = "You haven't saved anything yet",
                    isMainScreen = false
                )
            }
        }
    }
}


@Composable
fun PhotoCards(
    id: Int,
    url: String,
    author: String?,
    navController: NavHostController
){
    Box(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(24.dp)),
        contentAlignment = Alignment.BottomCenter
    ){
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    navController.navigate(Screen.Details.route + id.toString())
                },
            model = url,
            contentDescription = "Image",
            contentScale = ContentScale.FillWidth
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                .background(Color.Black.copy(0.4f)),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = author.toString(),
                color = White,
                fontFamily = FontFamily(Font(R.font.mulish_400)),
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}