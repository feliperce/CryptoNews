package io.github.feliperce.cryptonews

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import io.github.feliperce.cryptonews.di.initKoin
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    initKoin {

    }

    ComposeViewport(document.body!!) {
        App()
    }
}