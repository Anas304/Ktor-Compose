package org.example.project

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import networking.InsultsCensorClient
import utils.NetworkError
import utils.onError
import utils.onSuccess

@Composable
fun App(client: InsultsCensorClient) {

    MaterialTheme {

        var censoredText by remember { mutableStateOf<String?>(null) }
        var uncensoredText by remember { mutableStateOf("") }
        var isLoading by remember { mutableStateOf(false) }
        var errorMessage by remember { mutableStateOf<NetworkError?>(null) }
        val scope = rememberCoroutineScope()
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
        ) {
            TextField(
                value = uncensoredText,
                onValueChange = { uncensoredText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                placeholder = { Text(text = "Uncensored Text !") },
            )
            Button(
                onClick = {
                scope.launch {
                    isLoading = true
                    errorMessage = null

                    client.censorWords(uncensoredText)
                        .onSuccess { successResponse ->
                            censoredText = successResponse
                        }
                        .onError {
                            censoredText = it.toString()
                        }
                }
            }) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(15.dp),
                        strokeWidth = 1.dp,
                        color = MaterialTheme.colors.onPrimary
                    )
                } else {
                    Text("Censor !")
                }
            }
            censoredText?.let {
                Text(text = it)
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