package org.example.project

import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.ktor.client.engine.okhttp.OkHttp
import networking.CatAPIClient
import networking.createHttpClient
import org.example.project.ui.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Ktor-KMP",
    ) {
        App(
            client = remember {
                //Android specific Ktor-client
                CatAPIClient(createHttpClient(OkHttp.create()))
            }
        )
    }
}