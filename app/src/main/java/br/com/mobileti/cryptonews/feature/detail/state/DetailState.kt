package br.com.mobileti.cryptonews.feature.detail.state

import br.com.mobileti.cryptonews.feature.home.mapper.Article
import java.lang.Exception

data class DetailState(
    val loading: Boolean = false,
    val error: Exception? = null,
    val article: Article? = null
)

sealed class DetailIntent {
    class GetArticleById(newsId: Long)
}