package io.github.feliperce.cryptonews.feature.nav.view

import io.github.feliperce.cryptonews.feature.news.mapper.Article
import kotlinx.serialization.Serializable

sealed class Screen(
    val title: String,
    val route: String
) {
    data object NewsScreen : Screen(
        title = "News",
        route = "news"
    )
    data object NewsDetailScreen : Screen(
        title = "Detail",
        route = "detail"
    )
}

@Serializable
data class Details(
    val article: Article
)