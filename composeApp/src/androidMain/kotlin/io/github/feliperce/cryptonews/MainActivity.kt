package io.github.feliperce.cryptonews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import io.github.feliperce.cryptonews.ui.theme.primaryDark
import io.github.feliperce.cryptonews.ui.theme.primaryLight

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val statusBarColor = if (isSystemInDarkTheme()) {
                primaryDark
            } else {
                primaryLight
            }

            val activity = this@MainActivity
            activity.window.statusBarColor = statusBarColor.toArgb()

            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}