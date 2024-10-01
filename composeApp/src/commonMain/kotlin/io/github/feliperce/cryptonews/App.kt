package io.github.feliperce.cryptonews

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.crossfade
import coil3.util.DebugLogger
import org.jetbrains.compose.ui.tooling.preview.Preview

import io.github.feliperce.cryptonews.feature.nav.view.NavScreen

@OptIn(ExperimentalCoilApi::class)
@Composable
@Preview
fun App() {
    setSingletonImageLoaderFactory { context ->
        getAsyncImageLoader(context)
    }

    MaterialTheme {
        NavScreen()
    }
}

fun getAsyncImageLoader(context: PlatformContext) =
    ImageLoader.Builder(context).crossfade(true).logger(DebugLogger()).build()