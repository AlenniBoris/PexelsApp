package com.example.pexapp.uikit.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.pexapp.R

@Composable
fun AppTopBar(
    hasButton: Boolean,
    text: String,
    textVisible: Boolean,
    navController: NavHostController
){

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 17.dp)
            .padding(horizontal = if (text == stringResource(id = R.string.bookmarks_header)) 0.dp else 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (hasButton){
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .size(40.dp)
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back_button_description),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }

        if (textVisible){
            Text(
                text = text,
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.mulish_700)),
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}