package io.github.feliperce.cryptonews

import androidx.compose.ui.window.ComposeUIViewController
import io.github.feliperce.cryptonews.di.initKoin

fun MainViewController() = ComposeUIViewController {
    initKoin {  }
    App()
}