package com.example.pexapp.presentation.uikit.views

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.pexapp.R
import com.example.pexapp.presentation.uikit.theme.baseTextColor

@Composable
fun AppProgressBar(
    modifier: Modifier = Modifier,
) {

    val infiniteTransition = rememberInfiniteTransition(label = "")
    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = stringResource(R.string.animation_description)
    )

    Box(
        modifier = modifier
    ) {
        Icon(
            modifier = Modifier
                .align(Alignment.Center)
                .graphicsLayer(rotationZ = rotationAngle),

            painter = painterResource(R.drawable.progress_icon),
            tint = baseTextColor,
            contentDescription = stringResource(R.string.picture_description)
        )
    }
}