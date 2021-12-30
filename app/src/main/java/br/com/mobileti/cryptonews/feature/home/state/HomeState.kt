package br.com.mobileti.cryptonews.feature.home.state

import br.com.mobileti.cryptonews.data.exception.ErrorException
import br.com.mobileti.cryptonews.feature.home.mapper.Article
import br.com.mobileti.cryptonews.feature.home.mapper.CurrentNews
import java.lang.Exception

data class HomeUiState (
    val loading: Boolean = false,
    val error: ErrorException? = null,
    val articleList: List<Article> = listOf()
)

sealed class HomeIntent {
    class GetCurrentNews(val oldFormatDate: String, val newFormatDate: String) : HomeIntent()
    class RefreshNews(val oldFormatDate: String, val newFormatDate: String) : HomeIntent()
}