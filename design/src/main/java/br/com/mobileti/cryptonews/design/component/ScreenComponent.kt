package br.com.mobileti.cryptonews.design.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.style.TextOverflow
import br.com.mobileti.cryptonews.design.theme.Purple500

@Composable
fun CryptoNewsAppBar(
    title: String,
    titleMaxLines: Int = Int.MAX_VALUE,
    titleOverflow: TextOverflow = TextOverflow.Clip,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit)? = null
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                maxLines = titleMaxLines,
                overflow = titleOverflow
            )
        },
        backgroundColor = Purple500,
        contentColor = White,
        navigationIcon = navigationIcon,
        actions = { actions?.invoke(this) }
    )
}