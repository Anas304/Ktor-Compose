package org.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import io.ktor.client.engine.okhttp.OkHttp
import networking.CatAPIClient
import networking.createHttpClient
import org.example.project.ui.App

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App(
                client = remember {
                    //Android specific Ktor-client
                    CatAPIClient(createHttpClient(OkHttp.create()))
                }
            )
        }
    }
}
