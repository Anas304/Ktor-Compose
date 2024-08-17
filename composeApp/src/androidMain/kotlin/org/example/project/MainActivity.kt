package org.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import io.ktor.client.engine.okhttp.OkHttp
import networking.InsultsCensorClient
import networking.createHttpClient

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App(
                client = remember {
                    //Android specific Ktor-client
                    InsultsCensorClient(createHttpClient(OkHttp.create()))
                }
            )
        }
    }
}
