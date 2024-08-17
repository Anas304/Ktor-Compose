package org.example.project

import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.ktor.client.engine.okhttp.OkHttp
import networking.InsultsCensorClient
import networking.createHttpClient

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Ktor-KMP",
    ) {
        App(
            client = remember {
                //Android specific Ktor-client
                InsultsCensorClient(createHttpClient(OkHttp.create()))
            }
        )
    }
}