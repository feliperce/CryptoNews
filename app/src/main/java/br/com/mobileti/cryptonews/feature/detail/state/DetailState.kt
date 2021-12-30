package br.com.mobileti.cryptonews.feature.detail.state

import br.com.mobileti.cryptonews.feature.home.mapper.Article
import java.lang.Exception

data class DetailUiState(
    val loading: Boolean = false,
    val error: Exception? = null,
    val article: Article = Article()
)

sealed class DetailIntent {
    class GetArticleById(
        val articleId: Long,
        val oldDateFormat: String,
        val newDateFormat: String
    ) : DetailIntent()
}