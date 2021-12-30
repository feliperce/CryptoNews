package br.com.mobileti.cryptonews.feature.home.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.mobileti.cryptonews.R
import br.com.mobileti.cryptonews.extension.toFormattedDateString
import br.com.mobileti.cryptonews.feature.home.mapper.Article
import br.com.mobileti.cryptonews.feature.home.state.HomeIntent
import br.com.mobileti.cryptonews.feature.home.viewmodel.HomeViewModel
import br.com.mobileti.cryptonews.ui.component.CryptoNewsAppBar
import br.com.mobileti.cryptonews.ui.theme.HomeImageSize
import br.com.mobileti.cryptonews.ui.theme.MarginPaddingSizeMedium
import br.com.mobileti.cryptonews.ui.theme.Typography
import coil.compose.rememberImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = getViewModel()
) {
    val homeUiState by homeViewModel.homeState.collectAsState()
    var showProgress by remember { mutableStateOf(false)  }

    val scaffoldState = rememberScaffoldState()

    homeViewModel.sendIntent(HomeIntent.GetCurrentNews)

    showProgress = homeUiState.loading

    Home(
        scaffoldState = scaffoldState,
        articles = homeUiState.currentNews.lastOrNull()?.articles ?: listOf(),
        showProgress = showProgress,
        onRefresh = { homeViewModel.sendIntent(HomeIntent.RefreshNews) }
    )

}

@Composable
private fun Home(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    articles: List<Article>,
    showProgress: Boolean,
    onRefresh: () -> Unit
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            HomeAppBar()
        },
        content = {
            if (showProgress) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    CircularProgressIndicator()
                }
            }
            NewsItemList(
                articles = articles,
                onRefresh = { onRefresh() }
            )
        }
    )
}

@Composable
private fun NewsItemList(
    articles: List<Article>,
    onRefresh: () -> Unit
) {
    SwipeRefresh(
        state = rememberSwipeRefreshState(false),
        onRefresh = { onRefresh() },
    ) {
        LazyColumn {
            items(articles) {
                NewsItem(
                    title = it.title,
                    description = it.description,
                    newsDate = it.publishedAt,
                    imageUrl = it.urlToImage
                )
                Divider(color = Color.Black, thickness = 1.dp)
            }
        }
    }
}

@Composable
private fun NewsItem(
    title: String,
    description: String,
    newsDate: String,
    imageUrl: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MarginPaddingSizeMedium)
    ) {

        Image(
            modifier = Modifier
                .size(HomeImageSize)
                .padding(
                    end = MarginPaddingSizeMedium
                ),
            painter = rememberImagePainter(imageUrl),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
        Column {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = title,
                    style = Typography.h6
                )
            }
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = description,
                    style = Typography.subtitle1,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.fillMaxWidth())
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = newsDate.toFormattedDateString(
                        stringResource(id = R.string.date_service_pattern), 
                        stringResource(id = R.string.date_home_pattern)
                    ),
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.End,
                    style = Typography.body2
                )
            }
        }
    }
}

@Composable
private fun HomeAppBar() {
    CryptoNewsAppBar(title = stringResource(id = R.string.app_name))
}

@Composable
@Preview(showBackground = true)
private fun NewsItemListPreview() {
    NewsItemList(fakeNewsList, {})
}

@Composable
@Preview(showBackground = true)
private fun NewsItemPreview() {
    NewsItem(
        title = "Noticia bla bla bla bla bla",
        description = "Descrição bla bla bla bla bla bla bla bla",
        newsDate = "10/05/1990",
        imageUrl = ""
    )
}

@Composable
@Preview
private fun HomeAppBarPreview() {
    CryptoNewsAppBar(title = stringResource(id = R.string.app_name))
}

@Composable
@Preview
private fun HomePreview() {
    Home(
        articles = fakeNewsList,
        showProgress = true,
        onRefresh = {}
    )
}

private val fakeNewsList = listOf(
    Article(
        articleId = 0,
        newsId = 0,
        author = "Satoshi Nakamoto",
        content = "Lero lero lero lero lero lero",
        description = "Lero lero lero lero lero lero lero lero lero lero lero",
        publishedAt = "2021-11-26T01:35:06Z",
        title = "Noticia Titulo",
        url = "http://bitcoin.org",
        urlToImage = ""
    ),
    Article(
        articleId = 0,
        newsId = 0,
        author = "Satoshi Nakamoto 2",
        content = "Lero lero lero lero lero lero",
        description = "Lero lero lero",
        publishedAt = "2021-11-26T01:35:06Z",
        title = "Noticia Titulo",
        url = "http://bitcoin.org",
        urlToImage = ""
    ),
    Article(
        articleId = 0,
        newsId = 0,
        author = "Satoshi Nakamoto 4",
        content = "Lero lero",
        description = """Lero lero lero lero lero lero lero lero lero lero lero lero lero lero lero 
            |lero lero lero lero""".trimMargin(),
        publishedAt = "2021-11-26T01:35:06Z",
        title = "Noticia Titulo",
        url = "http://bitcoin.org",
        urlToImage = ""
    )
)