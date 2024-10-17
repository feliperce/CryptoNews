package io.github.feliperce.cryptonews.feature.detail.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import io.github.feliperce.cryptonews.feature.news.mapper.Article
import io.github.feliperce.cryptonews.ui.theme.MarginPaddingSizeMedium
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun NewsDetailScreen(
    navHostController: NavHostController,
    article: Article
) {
    Scaffold(
        scaffoldState = rememberScaffoldState(),
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = article.title,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = article.author,
                            fontWeight = FontWeight.W200,
                            maxLines = 1
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navHostController.popBackStack()
                        },
                        content = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = null
                            )
                        }
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            NewsDetailContent(
                article = article
            )
        }
    }
}

@Composable
fun NewsDetailContent(article: Article) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
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