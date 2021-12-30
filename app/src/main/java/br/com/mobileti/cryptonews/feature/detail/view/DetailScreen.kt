package br.com.mobileti.cryptonews.feature.detail.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import br.com.mobileti.cryptonews.R
import br.com.mobileti.cryptonews.feature.detail.state.DetailIntent
import br.com.mobileti.cryptonews.feature.detail.viewmodel.DetailViewModel
import br.com.mobileti.cryptonews.feature.home.mapper.Article
import br.com.mobileti.cryptonews.ui.component.CryptoNewsAppBar
import br.com.mobileti.cryptonews.ui.theme.DetailImageHeight
import br.com.mobileti.cryptonews.ui.theme.MarginPaddingSizeMedium
import br.com.mobileti.cryptonews.ui.theme.Typography
import coil.compose.rememberImagePainter
import org.koin.androidx.compose.getViewModel

@Composable
fun DetailScreen(
    detailViewModel: DetailViewModel = getViewModel(),
    articleId: Long
) {
    val detailUiState by detailViewModel.detailState.collectAsState()
    val scaffoldState = rememberScaffoldState()

    detailViewModel.sendIntent(DetailIntent.GetArticleById(articleId))

    val article = detailUiState.article

    Detail(
        scaffoldState = scaffoldState,
        article = article
    )
}

@Composable
private fun Detail(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    article: Article
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            DetailAppBar(title = article.title)
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
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MarginPaddingSizeMedium)
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
    Detail(article = fakeArticle)
}


private val fakeArticle: Article = Article(
    title = "News Title",
    publishedAt = "10/05/2010 10:05",
    author = "Authoooor",
    content = "Lero lero lero lero lero, lero lero lero lero. Lero lero."
)