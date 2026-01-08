package com.example.pexapp.presentation.uikit.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.pexapp.R
import com.example.pexapp.presentation.uikit.theme.appTextSize
import com.example.pexapp.presentation.uikit.theme.appTextStyle
import com.example.pexapp.presentation.uikit.theme.baseTextColor
import com.example.pexapp.presentation.uikit.theme.emptyScreenTextPadding
import com.example.pexapp.presentation.uikit.theme.extraTextColor
import com.example.pexapp.presentation.uikit.theme.extraTextSize

@Composable
fun AppEmptyScreen(
    modifier: Modifier = Modifier,
    onExploreClicked: () -> Unit = {},
    text: String = stringResource(R.string.nothing_found_text),
    btnText: String = stringResource(id = R.string.explore_text)
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            style = appTextStyle.copy(
                color = baseTextColor,
                fontSize = appTextSize
            )
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(emptyScreenTextPadding)
                .clickable {
                    onExploreClicked()
                },
            text = btnText,
            style = appTextStyle.copy(
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                fontSize = extraTextSize,
                color = extraTextColor
            )
        )
    }
}