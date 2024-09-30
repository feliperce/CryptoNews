package io.github.feliperce.cryptonews.feature.news.state

import androidx.compose.runtime.Immutable
import io.github.feliperce.cryptonews.data.remote.mapper.ErrorData
import io.github.feliperce.cryptonews.feature.news.mapper.News

@Immutable
data class NewsUiState (
    val loading: Boolean = false,
    val news: News? = null,
    val errorData: ErrorData? = null
)

sealed class NewsIntent {
    data object GetNews : NewsIntent()
}