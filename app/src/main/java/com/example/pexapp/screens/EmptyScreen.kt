package com.example.pexapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pexapp.R
import com.example.pexapp.uikit.theme.Red
import com.example.pexapp.uikit.theme.White

@Composable
fun EmptyScreen(
    onExploreClicked: () -> Unit,
    text: String,
    isMainScreen: Boolean,
    btnText: String = stringResource(id = R.string.explore_string),
    hasInternet: Boolean = true
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!hasInternet){
            Image(
                painter = painterResource(id =
                    if (MaterialTheme.colorScheme.background == White){
                        R.drawable.ic_no_network_light
                    } else{
                        R.drawable.ic_no_network_dark
                    }
                ),
                contentDescription = stringResource(id = R.string.wifi_error_description)
            )
        }else{
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = text,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.mulish_500)),
                fontSize = 14.sp
            )
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp)
                .clickable {
                    onExploreClicked()
                },
            text = btnText,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily(Font(R.font.mulish_700)),
            fontSize = 18.sp,
            color = Red
        )
    }
}