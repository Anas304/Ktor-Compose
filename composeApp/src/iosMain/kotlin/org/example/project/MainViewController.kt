package org.example.project

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import io.ktor.client.engine.darwin.Darwin
import networking.InsultsCensorClient
import networking.createHttpClient

fun MainViewController() = ComposeUIViewController { App(
    client = remember {
        //Android specific Ktor-client
        InsultsCensorClient(createHttpClient(Darwin.create()))
    }
) }