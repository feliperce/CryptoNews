package br.com.mobileti.cryptonews.ui.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.stringResource
import br.com.mobileti.cryptonews.ui.theme.Purple500

@Composable
fun CryptoNewsAppBar(
    title: String,
    navigationIcon: @Composable() (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit)? = null
) {
    TopAppBar(
        title = { Text(title) },
        backgroundColor = Purple500,
        contentColor = White,
        navigationIcon = navigationIcon,
        actions = { actions?.invoke(this) }
    )
}