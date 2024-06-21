package com.example.pexapp.screens

import android.graphics.Paint.Align
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.FileDownload
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.ResourceFont
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.pexapp.R
import com.example.pexapp.navigation.Route
import com.example.pexapp.navigation.Screen
import com.example.pexapp.ui.theme.Icon_Light_Theme
import com.example.pexapp.viewmodel.DetailsScreenViewModel
import dagger.hilt.android.qualifiers.ApplicationContext

@Composable
fun DetailsScreen(
    id: String?,
    navController: NavHostController,
    detailsScreenViewModel: DetailsScreenViewModel = hiltViewModel()
){

    val context = LocalContext.current
    val currentPhoto by detailsScreenViewModel.currentPhoto.collectAsStateWithLifecycle()

    detailsScreenViewModel.getPhotoFromPexelsById(id?.toInt())
    Log.d("detailsScreenViewModel.getPhotoFromPexelsById(id?.toInt())", currentPhoto.toString())

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {

        //Upper part
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .background(MaterialTheme.colorScheme.background),
            verticalAlignment = Alignment.CenterVertically
        ) {

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
                        contentDescription = "Button back",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }


            Text(
                text = currentPhoto?.photographer.toString(),
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.mulish_700)),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }

        //Image
        AsyncImage(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp)),
            model = currentPhoto?.src?.original,
            contentDescription = "Image",
            contentScale = ContentScale.FillWidth
        )

        //Buttons
        Row(
            modifier = Modifier.padding(vertical = 24.dp).background(MaterialTheme.colorScheme.background)
                .fillMaxWidth().height(48.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val screenWidth = LocalConfiguration.current.screenWidthDp.dp

            Row (
                modifier = Modifier
                    .clip(RoundedCornerShape(24.dp))
                    .width((screenWidth - 48.dp) / 2)
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable { Toast.makeText(context, "Download clicked", Toast.LENGTH_SHORT).show() },
                verticalAlignment = Alignment.CenterVertically
            ){
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
                        .size(48.dp)
                        .background(MaterialTheme.colorScheme.tertiary),
                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        imageVector = Icons.Outlined.Download,
                        contentDescription = "Button download",
                        tint = Color.White
                    )


                }
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Download",
                    textAlign = TextAlign.Center
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(24.dp))
                    .size(48.dp)
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable {
                        Toast.makeText(context, "Favourite clicked", Toast.LENGTH_SHORT).show()
                    },
                contentAlignment = Alignment.Center
            ) {

                    Icon(
                        painter = painterResource(id = R.drawable.icon_favourites_not_active),
                        contentDescription = "Button favourite",
                        tint = MaterialTheme.colorScheme.secondary
                    )

            }

        }

    }
}