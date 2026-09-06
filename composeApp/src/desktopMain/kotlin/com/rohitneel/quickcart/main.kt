package com.rohitneel.quickcart

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "QuickCart — KMP Grocery Demo",
        state = WindowState(width = 420.dp, height = 900.dp)
    ) {
        App()
    }
}
