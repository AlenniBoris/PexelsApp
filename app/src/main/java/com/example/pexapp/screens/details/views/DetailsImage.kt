package com.example.pexapp.screens.details.views

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pexapp.R
import com.example.pexapp.data.model.Photo

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
        contentDescription = stringResource(id = R.string.image_description_string),
        contentScale = ContentScale.FillWidth,
        placeholder =
        if (isSystemInDarkTheme()) painterResource(id = R.drawable.ic_placeholder_dark)
        else painterResource(id = R.drawable.ic_placeholder_light)
    )
}