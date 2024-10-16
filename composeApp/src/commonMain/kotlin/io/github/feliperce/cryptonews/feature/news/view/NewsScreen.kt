package io.github.feliperce.cryptonews.feature.news.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import io.github.feliperce.cryptonews.feature.news.mapper.Article
import io.github.feliperce.cryptonews.feature.news.mapper.News
import io.github.feliperce.cryptonews.feature.news.state.NewsIntent
import io.github.feliperce.cryptonews.feature.news.viewmodel.NewsViewModel
import io.github.feliperce.cryptonews.ui.theme.MarginPaddingSizeMedium
import io.github.feliperce.cryptonews.ui.theme.MarginPaddingSizeSmall
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NewsScreen(
    navHostController: NavHostController,
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
            NewsContent(
                news = news,
                onArticleClick = { article ->
                    navHostController.navigate(
                        article
                    )
                }
            )
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
    news: News,
    onArticleClick: (article: Article) -> Unit
) {
    Column {
        NewsItemList(
            articleList = news.articles,
            onArticleClick = onArticleClick
        )
    }
}

@Composable
fun NewsItem(
    article: Article,
    onArticleClick: (article: Article) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MarginPaddingSizeMedium)
            .clickable { onArticleClick(article) }
    ) {
        AsyncImage(
            modifier = Modifier.size(100.dp),
            model = article.urlToImage,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.padding(start = MarginPaddingSizeSmall)
        ) {
            Text(
                text = article.title,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = article.description,
                fontWeight = FontWeight.W200,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun NewsItemList(
    articleList: List<Article>,
    onArticleClick: (article: Article) -> Unit
) {
    LazyColumn() {
        items(
            items = articleList
        ) { article ->
            NewsItem(
                article = article,
                onArticleClick = onArticleClick
            )
            Divider()
        }
    }
}

@Composable
@Preview
fun NewsItemPreview() {
    MaterialTheme {
        NewsItem(
            article = fakeArticle,
            onArticleClick = {}
        )
    }
}

@Composable
@Preview
fun NewsItemListPreview() {
    MaterialTheme {
        NewsItemList(
            articleList = fakeArticleList,
            onArticleClick = {}
        )
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