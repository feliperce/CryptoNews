package io.github.feliperce.cryptonews.feature.nav.view

import io.github.feliperce.cryptonews.feature.news.mapper.Article
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object NewsScreen : Screen()

    @Serializable
    data class NewsDetailScreen(
        val article: Article
    ) : Screen()
}
