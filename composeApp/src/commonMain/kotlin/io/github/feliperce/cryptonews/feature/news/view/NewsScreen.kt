package io.github.feliperce.cryptonews.feature.news.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import io.github.feliperce.cryptonews.feature.news.mapper.Article
import io.github.feliperce.cryptonews.feature.news.mapper.News
import io.github.feliperce.cryptonews.feature.news.state.NewsIntent
import io.github.feliperce.cryptonews.feature.news.viewmodel.NewsViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NewsScreen(
    snackbarHostState: SnackbarHostState
) {
    val newsViewModel: NewsViewModel = koinViewModel()

    val newsUiState by newsViewModel.newsState.collectAsState()

    LaunchedEffect(Unit) {
        newsViewModel.sendIntent(
            NewsIntent.GetNews
        )
    }

    LaunchedEffect(newsUiState.errorData?.id) {
        newsUiState.errorData?.let { errorData ->
            snackbarHostState
                .showSnackbar(
                    message = errorData.message
                )
        }
    }

    Column {
        newsUiState.news?.let { news ->
            NewsContent(news)
        } ?: run {
            Text("dasdasdasdasdasdsa")
        }
    }

    if (newsUiState.loading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

}

@Composable
fun NewsContent(
    news: News
) {

    Column {
        NewsItemList(news.articles)
    }

}

@Composable
fun NewsItem(article: Article) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        AsyncImage(
            modifier = Modifier.size(50.dp),
            model = article.urlToImage,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Row {
            Text(
                text = article.title
            )

            Text(
                text = article.description
            )
        }
    }
}

@Composable
fun NewsItemList(articleList: List<Article>) {
    LazyColumn() {
        items(
            items = articleList
        ) { article ->
            NewsItem(article)
        }
    }
}

@Composable
@Preview
fun NewsItemPreview() {
    MaterialTheme {
        NewsItem(fakeArticle)
    }
}

@Composable
@Preview
fun NewsItemListPreview() {
    MaterialTheme {
        NewsItemList(fakeArticleList)
    }
}


private val fakeArticle: Article = Article(
    urlToImage = "https://pngimg.com/d/android_logo_PNG2.png",
    author = "Authoor",
    content = "Content, content, content content",
    title = "Titleee, title, title",
    description = "Description"
)

private val fakeArticleList = listOf(
    fakeArticle,
    fakeArticle,
    fakeArticle
)