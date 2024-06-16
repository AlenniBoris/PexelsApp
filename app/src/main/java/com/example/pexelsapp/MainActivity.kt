package com.example.pexelsapp

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.room.util.TableInfo
import com.example.pexelsapp.entities.Dummy
import com.example.pexelsapp.entities.SelectedImage
import com.example.pexelsapp.ui.theme.PexelsAppTheme
import com.example.pexelsapp.viewmodel.SelectedImagesViewModel
import androidx.compose.runtime.collectAsState as collectAsState


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PexelsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val context = LocalContext.current
                    show(context = context)
                }
            }
        }
    }
}

@Composable
fun show(
    viewM: SelectedImagesViewModel = viewModel(),
    context: Context
){
    var iid by remember{ mutableStateOf("") }
    var img by remember{ mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TextField(value = iid, onValueChange = { iid = it }, modifier = Modifier
            .fillMaxWidth()
            .height(50.dp))
        Spacer(modifier = Modifier.height(50.dp))
        TextField(value = img, onValueChange = { img = it }, modifier = Modifier
            .fillMaxWidth()
            .height(50.dp))
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            viewM.addSelectedImage(
                SelectedImage(
                    imageId = iid.toLong(), imageString = img
                )
            )
            Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show()
        }) {
            Text(text = "Add")
        }
        Spacer(modifier = Modifier.height(100.dp))
                    val imgItems = viewM.getAllSelectedImages.collectAsState(initial = listOf())
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        )
        {
            items(imgItems.value){item ->
                Text(text = "id = ${item.id}")
                Spacer(modifier = Modifier.height(1.dp))
                Text(text = "Img id = ${item.imageId}")
                Spacer(modifier = Modifier.height(1.dp))
                Text(text = "Img = ${item.imageString}")
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }

}
