package com.example.pexapp.presentation.uikit.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pexapp.R
import com.example.pexapp.presentation.uikit.theme.PexAppTheme
import com.example.pexapp.presentation.uikit.theme.appColor
import com.example.pexapp.presentation.uikit.theme.appTextStyle
import com.example.pexapp.presentation.uikit.theme.baseTextColor
import com.example.pexapp.presentation.uikit.theme.clickableElementBackground
import com.example.pexapp.presentation.uikit.theme.extraTextSize
import com.example.pexapp.presentation.uikit.theme.topBarButtonInnerPadding
import com.example.pexapp.presentation.uikit.theme.topBarButtonOuterPadding
import com.example.pexapp.presentation.uikit.theme.topBarButtonShape
import com.example.pexapp.presentation.uikit.theme.topBarInnerPadding

@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    trailingIcon: Painter? = null,
    onTrailingClicked: () -> Unit = {},
    text: String
) {

    Box(
        modifier = modifier
    ) {
        trailingIcon?.let {
            Icon(
                modifier = Modifier
                    .padding(topBarButtonOuterPadding)
                    .clip(topBarButtonShape)
                    .background(clickableElementBackground)
                    .padding(topBarButtonInnerPadding)
                    .clickable { onTrailingClicked() },
                painter = trailingIcon,
                tint = baseTextColor,
                contentDescription = stringResource(R.string.picture_description)
            )
        }

        Text(
            modifier = Modifier.align(Alignment.Center),
            text = text,
            style = appTextStyle.copy(
                color = baseTextColor,
                fontSize = extraTextSize,
                fontWeight = FontWeight.Medium
            )
        )
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
                    .fillMaxWidth()
                    .background(appColor)
            ) {
                AppTopBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(topBarInnerPadding),
                    text = "skamkldsmaskdm"
                )
                Spacer(Modifier.height(20.dp))
                AppTopBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(topBarInnerPadding),
                    trailingIcon = painterResource(R.drawable.navigate_back),
                    text = "skamkldsmaskdm"
                )
                Spacer(Modifier.height(20.dp))
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
                    .fillMaxWidth()
                    .background(appColor)
            ) {
                AppTopBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(topBarInnerPadding),
                    text = "skamkldsmaskdm"
                )
                Spacer(Modifier.height(20.dp))
                AppTopBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(topBarInnerPadding),
                    trailingIcon = painterResource(R.drawable.navigate_back),
                    text = "skamkldsmaskdm"
                )
                Spacer(Modifier.height(20.dp))
            }
        }
    }
}