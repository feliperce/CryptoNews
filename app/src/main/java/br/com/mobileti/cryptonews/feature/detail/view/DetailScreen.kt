package br.com.mobileti.cryptonews.feature.detail.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import br.com.mobileti.cryptonews.R
import br.com.mobileti.cryptonews.extension.toFormattedDateString
import br.com.mobileti.cryptonews.feature.home.mapper.Article
import br.com.mobileti.cryptonews.ui.component.CryptoNewsAppBar
import br.com.mobileti.cryptonews.ui.theme.HomeImageSize
import br.com.mobileti.cryptonews.ui.theme.MarginPaddingSizeMedium
import br.com.mobileti.cryptonews.ui.theme.Typography
import coil.compose.rememberImagePainter

@Composable
private fun Detail(
    imageUrl: String,
    author: String,
    content: String,
    publishedAt: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MarginPaddingSizeMedium)
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth(),
            painter = rememberImagePainter(imageUrl),
            contentDescription = "",
            contentScale = ContentScale.FillBounds
        )
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.detail_author, author, publishedAt),
                style = Typography.body2,
                fontStyle = FontStyle.Italic
            )
        }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = MarginPaddingSizeMedium),
            text = content
        )
    }
}

@Composable
private fun DetailAppBar(title: String) {
    CryptoNewsAppBar(title = title)
}

@Preview
@Composable
private fun DetailAppBarPreview() {
    DetailAppBar(title = "News Title")
}

@Preview(showBackground = true)
@Composable
private fun DetailPreview() {
    Detail(
        imageUrl = "",
        author = fakeAuthor,
        content = fakeContent,
        publishedAt = fakePublishedAt
    )
}

private const val fakeAuthor = "Authooor"
private const val fakeContent = "Lero lero lero lero lero, lero lero lero lero. Lero lero."
private const val fakePublishedAt = "10/05/2010 10:05"