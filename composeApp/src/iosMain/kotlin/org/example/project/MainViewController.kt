package org.example.project

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import io.ktor.client.engine.darwin.Darwin
import networking.CatAPIClient
import networking.createHttpClient
import org.example.project.ui.App

fun MainViewController() = ComposeUIViewController { App(
    client = remember {
        //Android specific Ktor-client
        CatAPIClient(createHttpClient(Darwin.create()))
    }
) }