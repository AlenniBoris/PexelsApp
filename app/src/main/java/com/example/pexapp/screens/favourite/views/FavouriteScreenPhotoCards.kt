package com.example.pexapp.screens.favourite.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.pexapp.R
import com.example.pexapp.navigation.Screen
import com.example.pexapp.uikit.theme.White

@Composable
fun PhotoCards(
    id: Int,
    url: String,
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
            contentScale = ContentScale.FillWidth
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                .background(Color.Black.copy(0.4f)),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = author.toString(),
                color = White,
                fontFamily = FontFamily(Font(R.font.mulish_400)),
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}