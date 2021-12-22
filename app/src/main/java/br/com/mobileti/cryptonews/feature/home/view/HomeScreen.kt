package br.com.mobileti.cryptonews.feature.home.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.mobileti.cryptonews.R
import br.com.mobileti.cryptonews.feature.home.mapper.Article
import br.com.mobileti.cryptonews.feature.home.mapper.CurrentNews
import br.com.mobileti.cryptonews.feature.home.state.HomeIntent
import br.com.mobileti.cryptonews.feature.home.viewmodel.HomeViewModel
import br.com.mobileti.cryptonews.ui.component.CryptoNewsAppBar
import br.com.mobileti.cryptonews.ui.theme.HomeImageSize
import br.com.mobileti.cryptonews.ui.theme.MarginPaddingSizeMedium
import br.com.mobileti.cryptonews.ui.theme.Typography
import coil.compose.rememberImagePainter
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = getViewModel()
) {
    val homeUiState by homeViewModel.homeState.collectAsState()
    var showProgressState by remember { mutableStateOf(false)  }

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(homeViewModel.intentChannel) {
        homeViewModel.intentChannel.send(
            HomeIntent.GetCurrentNews
        )
    }

    Home(
        scaffoldState = scaffoldState,
        articles = homeUiState.currentNews.lastOrNull()?.articles ?: listOf()
    )

}

@Composable
fun Home(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    articles: List<Article>
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            HomeAppBar()
        },
        content = {
            NewsItemList(articles = articles)
        }
    )
}

@Composable
fun NewsItemList(
    articles: List<Article>
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

@Composable
fun NewsItem(
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
                    style = Typography.subtitle1
                )
                Spacer(modifier = Modifier.fillMaxWidth())
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = newsDate,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.End,
                    style = Typography.body2
                )
            }
        }
    }
}

@Composable
fun HomeAppBar() {
    CryptoNewsAppBar(title = R.string.app_name)
}

@Composable
@Preview(showBackground = true)
fun NewsItemListPreview() {
    NewsItemList(fakeNewsList)
}

@Composable
@Preview(showBackground = true)
fun NewsItemPreview() {
    NewsItem(
        title = "Noticia bla bla bla bla bla",
        description = "Descrição bla bla bla bla bla bla bla bla",
        newsDate = "10/05/1990",
        imageUrl = ""
    )
}

@Composable
@Preview
fun HomeAppBarPreview() {
    CryptoNewsAppBar(title = R.string.app_name)
}

@Composable
@Preview
fun HomePreview() {
    Home(articles = fakeNewsList)
}

private val fakeNewsList = listOf(
    Article(
        articleId = 0,
        newsId = 0,
        author = "Satoshi Nakamoto",
        content = "Lero lero lero lero lero lero",
        description = "Lero lero lero lero lero lero lero lero lero lero lero",
        publishedAt = "10/11/2008",
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
        publishedAt = "10/11/2008",
        title = "Noticia Titulo",
        url = "http://bitcoin.org",
        urlToImage = ""
    ),
    Article(
        articleId = 0,
        newsId = 0,
        author = "Satoshi Nakamoto 4",
        content = "Lero lero",
        description = "Lero lero lero lero lero lero lero lero lero lero lero lero lero lero lero",
        publishedAt = "10/11/2008",
        title = "Noticia Titulo",
        url = "http://bitcoin.org",
        urlToImage = ""
    )
)