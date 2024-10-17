package io.github.feliperce.cryptonews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val activity = this@MainActivity
            activity.window.statusBarColor = MaterialTheme.colors.primary.toArgb()

            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}