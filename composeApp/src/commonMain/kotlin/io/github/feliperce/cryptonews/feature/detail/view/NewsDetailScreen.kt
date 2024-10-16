package io.github.feliperce.cryptonews.feature.detail.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import io.github.feliperce.cryptonews.feature.nav.view.Screen
import io.github.feliperce.cryptonews.feature.news.mapper.Article
import io.github.feliperce.cryptonews.ui.theme.MarginPaddingSizeMedium
import io.github.feliperce.cryptonews.ui.theme.MarginPaddingSizeSmall
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun NewsDetailScreen(
    navHostController: NavHostController,
    article: Article
) {


    NewsDetailContent(
        article = article
    )
}

@Composable
fun NewsDetailContent(article: Article) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxWidth(),
            model = article.urlToImage,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MarginPaddingSizeMedium)
        ) {
            Text(
                text = article.content,
                fontWeight = FontWeight.W600,
            )
        }
    }
}

@Composable
@Preview
fun NewsDetailContentPreview() {
    NewsDetailContent(
        article = fakeArticle
    )
}

private val fakeArticle: Article = Article(
    urlToImage = "https://pngimg.com/d/android_logo_PNG2.png",
    author = "Authoor",
    content = "Content, content, content content",
    title = "Titleee, title, title",
    description = "Description"
)