package com.example.pexapp.screens.main.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.pexapp.R
import com.example.pexapp.navigation.Screen

@Composable
fun PhotoCard(
    id: Int?,
    url: String?,
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
            contentDescription = stringResource(id = R.string.image_description_string),
            contentScale = ContentScale.FillWidth,
            placeholder =
            if (isSystemInDarkTheme()) painterResource(id = R.drawable.ic_placeholder_dark)
            else painterResource(id = R.drawable.ic_placeholder_light)
        )
    }
}