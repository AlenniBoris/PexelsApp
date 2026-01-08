package com.example.pexapp.presentation.uikit.views

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import coil.compose.AsyncImage
import com.example.pexapp.R
import com.example.pexapp.presentation.model.PhotoModelUi
import com.example.pexapp.presentation.uikit.theme.appPictureTextInnerPadding
import com.example.pexapp.presentation.uikit.theme.appTextSize
import com.example.pexapp.presentation.uikit.theme.appTextStyle
import com.example.pexapp.presentation.uikit.theme.pictureTextBackgroundColor
import com.example.pexapp.presentation.uikit.theme.pictureTextColor

@Composable
fun AppPhotoCard(
    modifier: Modifier = Modifier,
    photo: PhotoModelUi,
    isSimple: Boolean = false
) {
    Box(
        modifier = modifier
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = if (isSimple) photo.simpleModel?.photoPictureMediumSizeUrl else photo.domainModel?.src?.medium,
            contentDescription = stringResource(id = R.string.image_description_string),
            contentScale = ContentScale.Companion.FillWidth,
            placeholder =
                if (isSystemInDarkTheme()) painterResource(id = R.drawable.ic_placeholder_dark)
                else painterResource(id = R.drawable.ic_placeholder_light)
        )

        if (!isSimple) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(pictureTextBackgroundColor)
                    .padding(appPictureTextInnerPadding)
                    .align(Alignment.Companion.BottomCenter),
                text = photo.domainModel?.photographer ?: stringResource(R.string.nan_text),
                style = appTextStyle.copy(
                    color = pictureTextColor,
                    fontSize = appTextSize,
                    textAlign = TextAlign.Companion.Center
                )
            )
        }
    }
}