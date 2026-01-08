package com.example.pexapp.presentation.uikit.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.pexapp.R
import com.example.pexapp.domain.model.CommonExceptionModelDomain
import com.example.pexapp.presentation.model.ExceptionModelUi
import com.example.pexapp.presentation.model.toUiModel
import com.example.pexapp.presentation.uikit.theme.PexAppTheme
import com.example.pexapp.presentation.uikit.theme.appColor
import com.example.pexapp.presentation.uikit.theme.appTextSize
import com.example.pexapp.presentation.uikit.theme.appTextStyle
import com.example.pexapp.presentation.uikit.theme.baseTextColor
import com.example.pexapp.presentation.uikit.theme.exceptionScreenTextPadding
import com.example.pexapp.presentation.uikit.theme.extraTextColor
import com.example.pexapp.presentation.uikit.theme.extraTextSize

@Composable
fun AppExceptionScreen(
    modifier: Modifier = Modifier,
    exception: ExceptionModelUi,
    onTryAgain: () -> Unit = {}
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Icon(
            painter = painterResource(exception.exceptionIconResource),
            tint = baseTextColor,
            contentDescription = stringResource(exception.exceptionStringResource)
        )

        Text(
            modifier = Modifier
                .padding(exceptionScreenTextPadding)
                .clickable { onTryAgain() },
            text = stringResource(exception.exceptionStringResource),
            style = appTextStyle.copy(
                color = baseTextColor,
                fontSize = appTextSize,
                textAlign = TextAlign.Center
            )
        )

        Text(
            modifier = Modifier
                .padding(exceptionScreenTextPadding)
                .clickable { onTryAgain() },
            text = stringResource(R.string.try_again_string),
            style = appTextStyle.copy(
                color = extraTextColor,
                fontSize = extraTextSize,
                textAlign = TextAlign.Center
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
            AppExceptionScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .background(appColor),
                exception = CommonExceptionModelDomain.InternetException.toUiModel()
            )
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
            AppExceptionScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .background(appColor),
                exception = CommonExceptionModelDomain.InternetException.toUiModel()
            )
        }
    }
}