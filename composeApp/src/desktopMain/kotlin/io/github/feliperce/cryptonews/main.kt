package io.github.feliperce.cryptonews

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.window.*
import cryptonews.composeapp.generated.resources.Res
import cryptonews.composeapp.generated.resources.compose_multiplatform
import io.github.feliperce.cryptonews.di.initKoin
import org.jetbrains.compose.resources.painterResource

fun main() = application {

    initKoin {

    }

    MaterialTheme {
        val state = rememberWindowState(
            placement = WindowPlacement.Maximized,
            position = WindowPosition(Alignment.Center)
        )

        Window(
            onCloseRequest = ::exitApplication,
            title = "CryptoNews",
            state = state,
            icon = painterResource(Res.drawable.compose_multiplatform)
        ) {
            App()
        }
    }
}