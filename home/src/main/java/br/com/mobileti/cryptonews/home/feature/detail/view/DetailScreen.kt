package br.com.mobileti.cryptonews.home.feature.detail.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.com.mobileti.cryptonews.home.feature.detail.state.DetailIntent
import br.com.mobileti.cryptonews.feature.detail.viewmodel.DetailViewModel
import br.com.mobileti.cryptonews.home.feature.home.mapper.Article
import br.com.mobileti.cryptonews.home.R
import br.com.mobileti.cryptonews.design.component.CryptoNewsAppBar
import br.com.mobileti.cryptonews.design.theme.MarginPaddingSizeMedium
import br.com.mobileti.cryptonews.design.theme.Typography
import br.com.mobileti.cryptonews.home.ui.theme.DetailImageHeight
import coil.compose.rememberImagePainter
import org.koin.androidx.compose.getViewModel

@Composable
fun DetailScreen(
    navController: NavHostController = rememberNavController(),
    detailViewModel: DetailViewModel = getViewModel(),
    articleId: Long
) {
    val detailUiState by detailViewModel.detailState.collectAsState()
    val scaffoldState = rememberScaffoldState()

    detailViewModel.sendIntent(
        DetailIntent.GetArticleById(
            articleId = articleId,
            oldDateFormat = stringResource(id = R.string.date_service_pattern),
            newDateFormat = stringResource(id = R.string.date_detail_pattern)
        )
    )

    val article = detailUiState.article

    Detail(
        scaffoldState = scaffoldState,
        article = article,
        onBackClick = { navController.popBackStack() }
    )
}

@Composable
private fun Detail(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    article: Article,
    onBackClick: () -> Unit
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            DetailAppBar(
                title = article.title,
                onBackClick = onBackClick
            )
        },
        content = {
            DetailContent(
                imageUrl = article.urlToImage,
                author = article.author,
                content = article.content,
                publishedAt = article.publishedAt
            )
        }
    )
}

@Composable
private fun DetailContent(
    imageUrl: String,
    author: String,
    content: String,
    publishedAt: String
) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MarginPaddingSizeMedium)
            .verticalScroll(scrollState)
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(DetailImageHeight),
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
private fun DetailAppBar(
    title: String,
    onBackClick: () -> Unit
) {
    CryptoNewsAppBar(
        title = title,
        titleMaxLines = 1,
        titleOverflow = TextOverflow.Ellipsis,
        navigationIcon = {
            IconButton(onClick = { onBackClick() }) {
                Icon(Icons.Filled.ArrowBack,"")
            }
        }
    )
}

@Preview
@Composable
private fun DetailAppBarPreview() {
    DetailAppBar(
        title = "News Title News Title News Title News Title News Title News Title",
        onBackClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun DetailContentPreview() {
    DetailContent(
        imageUrl = "",
        author = fakeArticle.author,
        content = fakeArticle.content,
        publishedAt = fakeArticle.publishedAt
    )
}

@Preview(showBackground = true)
@Composable
private fun DetailPreview() {
    Detail(
        article = fakeArticle,
        onBackClick = {}
    )
}


private val fakeArticle: Article = Article(
    title = "News Title",
    publishedAt = "10/05/2010 10:05",
    author = "Authoooor",
    content = "Lero lero lero lero lero, lero lero lero lero. Lero lero."
)