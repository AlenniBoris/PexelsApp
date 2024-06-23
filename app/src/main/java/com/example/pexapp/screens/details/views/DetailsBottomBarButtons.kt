package com.example.pexapp.screens.details.views

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.pexapp.R
import com.example.pexapp.data.model.Photo
import com.example.pexapp.navigation.Route
import com.example.pexapp.screens.details.DetailsScreenViewModel
import com.example.pexapp.utils.Downloader

@Composable
fun DetailsBottomBarButtons(
    context: Context,
    detailsScreenViewModel: DetailsScreenViewModel,
    currentPhoto: Photo,
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
                        currentPhoto.src.original,
                        currentPhoto.alt
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
                    contentDescription = stringResource(id = R.string.download_button_description),
                    tint = Color.White
                )
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.download_button_text),
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
                        Route.MainRoute.routeToScreen -> {
                            detailsScreenViewModel.actionOnFavouriteButton(currentPhoto)
                        }

                        Route.FavouriteRoute.routeToScreen -> {
                            detailsScreenViewModel.actionOnFavouriteButton(currentPhoto)
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
                contentDescription = stringResource(id = R.string.favourite_button_description),
                tint = MaterialTheme.colorScheme.secondary
            )
        }

    }
}