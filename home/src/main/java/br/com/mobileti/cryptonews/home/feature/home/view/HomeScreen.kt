package br.com.mobileti.cryptonews.home.feature.home.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.com.mobileti.cryptonews.design.component.CryptoNewsAppBar
import br.com.mobileti.cryptonews.design.theme.MarginPaddingSizeMedium
import br.com.mobileti.cryptonews.design.theme.Typography
import br.com.mobileti.cryptonews.home.feature.home.mapper.Article
import br.com.mobileti.cryptonews.home.feature.home.state.HomeIntent
import br.com.mobileti.cryptonews.home.feature.home.viewmodel.HomeViewModel
import br.com.mobileti.cryptonews.home.R
import br.com.mobileti.cryptonews.home.ui.theme.HomeImageSize
import coil.compose.rememberImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeScreen(
    navController: NavHostController = rememberNavController(),
    homeViewModel: HomeViewModel = getViewModel()
) {
    val homeUiState by homeViewModel.homeState.collectAsState()
    var showProgress by remember { mutableStateOf(false)  }

    val scaffoldState = rememberScaffoldState()

    val oldPattern = stringResource(id = R.string.date_service_pattern)
    val newPattern = stringResource(id = R.string.date_home_pattern)

    homeViewModel.sendIntent(
        HomeIntent.GetCurrentNews(
            oldFormatDate = oldPattern,
            newFormatDate = newPattern
        )
    )

    showProgress = homeUiState.loading

    Home(
        scaffoldState = scaffoldState,
        articles = homeUiState.articleList,
        showProgress = showProgress,
        onRefresh = {
            homeViewModel.sendIntent(
                HomeIntent.RefreshNews(
                    oldFormatDate = oldPattern,
                    newFormatDate = newPattern
                )
            )
        },
        onItemClick = { navController.navigate("detail/${it.articleId}") }
    )

}

@Composable
private fun Home(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    articles: List<Article>,
    showProgress: Boolean,
    onRefresh: () -> Unit,
    onItemClick: (article: Article) -> Unit
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
                onRefresh = onRefresh,
                onItemClick = onItemClick
            )
        }
    )
}

@Composable
private fun NewsItemList(
    articles: List<Article>,
    onRefresh: () -> Unit,
    onItemClick: (article: Article) -> Unit
) {
    SwipeRefresh(
        state = rememberSwipeRefreshState(false),
        onRefresh = onRefresh,
    ) {
        LazyColumn {
            items(articles) { article ->
                NewsItem(
                    article = article,
                    onItemClick = onItemClick
                )
                Divider(color = Color.Black, thickness = 1.dp)
            }
        }
    }
}

@Composable
private fun NewsItem(
    article: Article,
    onItemClick: (article: Article) -> Unit
) {
    Row(
        modifier = Modifier
            .clickable {
                onItemClick(article)
            }
            .fillMaxWidth()
            .padding(MarginPaddingSizeMedium)
    ) {

        Image(
            modifier = Modifier
                .size(HomeImageSize)
                .padding(
                    end = MarginPaddingSizeMedium
                ),
            painter = rememberImagePainter(article.urlToImage),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
        Column {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = article.title,
                    style = Typography.h6
                )
            }
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = article.description,
                    style = Typography.subtitle1,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.fillMaxWidth())
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = article.publishedAt,
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
    NewsItemList(fakeNewsList, {}, {})
}

@Composable
@Preview(showBackground = true)
private fun NewsItemPreview() {
    NewsItem(
        article = fakeNewsList[0],
        onItemClick = {}
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
        onRefresh = {},
        onItemClick = {}
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