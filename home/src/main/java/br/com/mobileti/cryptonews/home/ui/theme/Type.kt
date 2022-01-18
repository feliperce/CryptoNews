package br.com.mobileti.cryptonews.home.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import br.com.mobileti.cryptonews.home.ui.theme.TextSizeLarge
import br.com.mobileti.cryptonews.home.ui.theme.TextSizeMedium
import br.com.mobileti.cryptonews.home.ui.theme.TextSizeSmall

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = TextSizeMedium
    ),
    body2 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = TextSizeSmall
    ),
    subtitle1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = TextSizeMedium
    ),
    h6 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = TextSizeLarge
    )

)