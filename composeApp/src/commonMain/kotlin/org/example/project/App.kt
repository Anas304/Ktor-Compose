package org.example.project

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberImagePainter
import ktor_kmp.composeapp.generated.resources.Res
import ktor_kmp.composeapp.generated.resources.error_place_holder
import networking.CatDTO
import networking.InsultsCensorClient
import org.jetbrains.compose.resources.painterResource
import utils.NetworkError
import utils.onError
import utils.onSuccess

@Composable
fun App(client: InsultsCensorClient) {

    MaterialTheme {
        val catsList = remember { mutableStateListOf<CatDTO?>() }
        val numberOfCatImage by remember { mutableStateOf(5) }
        val errorMessage by remember { mutableStateOf<NetworkError?>(null) }

        LaunchedEffect(Unit){
            client.getCatImages(numberOfCatsImage = numberOfCatImage)
                .onSuccess { successResponse ->
                    println("Cat result are : $successResponse")
                    catsList.clear()
                    catsList.addAll(successResponse)
                }
                .onError {
                    println("Cat result error is : $it")
                }
        }

        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(catsList) { item->
                    if (item != null) {
                        CatItemCard(imageURL = item.url)
                    }
                }
            }
            errorMessage?.let {
                Text(
                    text = it.name,
                    color = Color.Red
                )
            }
        }
    }
}

@Composable
fun CatItemCard(
    modifier: Modifier = Modifier,
    imageURL:String
    ) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        backgroundColor = MaterialTheme.colors.onBackground,
        shape = RoundedCornerShape(13.dp),
        border = BorderStroke(1.dp,Color.Transparent)
    ){
        Image(
            modifier = modifier
                .fillMaxWidth(),
            painter = rememberImagePainter(
                url = imageURL,
                errorPainter = {
                    painterResource(Res.drawable.error_place_holder)
                }
            ),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
    }
}
