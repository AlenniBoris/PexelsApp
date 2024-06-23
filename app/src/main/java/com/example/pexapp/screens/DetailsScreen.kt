package com.example.pexapp.screens

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.example.pexapp.entity.Photo
import com.example.pexapp.navigation.Route
import com.example.pexapp.utils.AppLoadingProgressBar
import com.example.pexapp.utils.AppTopBar
import com.example.pexapp.utils.Downloader
import com.example.pexapp.viewmodel.DetailsScreenViewModel

@Composable
fun DetailsScreen(
    id: String?,
    navController: NavHostController,
    detailsScreenViewModel: DetailsScreenViewModel = hiltViewModel()
){

    val context = LocalContext.current
    val currentPhoto by detailsScreenViewModel.currentPhoto.collectAsStateWithLifecycle()
    val currentPhotoIsFavourite by detailsScreenViewModel.photoIsFavourite.collectAsStateWithLifecycle()

    val prevRoute = navController.previousBackStackEntry?.destination?.route

    when(prevRoute){
        Route.mainRoute.routeToScreen -> detailsScreenViewModel.getPhotoFromPexelsById(id?.toInt())
        Route.favouriteRoute.routeToScreen -> detailsScreenViewModel.getPhotoFromFavouritesDatabaseById(id?.toInt())
    }

    Log.d("DetailsScreen", currentPhoto.toString())

    val window = (context as Activity).window
    window.statusBarColor = MaterialTheme.colorScheme.background.toArgb()

    val scrollState = rememberScrollState()

    AppLoadingProgressBar(isLoading = (currentPhoto == null))

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                hasButton = true,
                text = currentPhoto?.photographer.toString(),
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
            if (currentPhoto != null){
                DetailsImage(currentPhoto = currentPhoto)
                DetailsBottomBarButtons(
                    context = context,
                    detailsScreenViewModel = detailsScreenViewModel,
                    currentPhoto = currentPhoto!!,
                    currentPhotoIsInFavourite = currentPhotoIsFavourite,
                    id = id?.toInt()!!,
                    navController = navController
                )
            }else{
                EmptyScreen(
                    isMainScreen = false,
                    onExploreClicked = {
                        navController.popBackStack()
                    },
                    text = "Image not found"
                )
            }
        }
    }
}

@Composable
fun DetailsImage(
    currentPhoto: Photo?
){
    AsyncImage(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp)
            .clip(RoundedCornerShape(24.dp)),
        model = currentPhoto?.src?.original,
        contentDescription = "Image",
        contentScale = ContentScale.FillWidth,
        placeholder = 
            if (isSystemInDarkTheme()) painterResource(id = R.drawable.ic_placeholder_dark)
            else painterResource(id = R.drawable.ic_placeholder_light)
    )
}

@Composable
fun DetailsBottomBarButtons(
    context: Context,
    detailsScreenViewModel: DetailsScreenViewModel,
    currentPhoto: Photo?,
    currentPhotoIsInFavourite: Boolean,
    id: Int,
    navController: NavHostController
){
    val downloader = Downloader(context)

    Row(
        modifier = Modifier
            .padding(vertical = 24.dp)
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .height(48.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val screenWidth = LocalConfiguration.current.screenWidthDp.dp

        Row (
            modifier = Modifier
                .clip(RoundedCornerShape(24.dp))
                .width((screenWidth - 48.dp) / 2)
                .background(MaterialTheme.colorScheme.primary)
                .clickable {
                    downloader.downloadFile(
                        currentPhoto?.src?.original.toString(),
                        currentPhoto?.alt.toString()
                    )
                },
            verticalAlignment = Alignment.CenterVertically
        ){
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(24.dp))
                    .size(48.dp)
                    .background(MaterialTheme.colorScheme.tertiary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Download,
                    contentDescription = "Button download",
                    tint = Color.White
                )
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Download",
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.mulish_600)),
                fontSize = 14.sp
            )
        }

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(24.dp))
                .size(48.dp)
                .background(MaterialTheme.colorScheme.primary)
                .clickable {
                    val prevRoute = navController.previousBackStackEntry?.destination?.route

                    when (prevRoute) {
                        Route.mainRoute.routeToScreen -> {
                            detailsScreenViewModel.actionOnFavouriteButton(currentPhoto!!)
                        }

                        Route.favouriteRoute.routeToScreen -> {
                            detailsScreenViewModel.actionOnFavouriteButton(currentPhoto!!)
                            navController.popBackStack()
                        }
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(
                    id =
                    if(currentPhotoIsInFavourite) R.drawable.icon_favourites_active
                    else R.drawable.icon_favourites_not_active),
                contentDescription = "Button favourite",
                tint = MaterialTheme.colorScheme.secondary
            )
        }

    }
}