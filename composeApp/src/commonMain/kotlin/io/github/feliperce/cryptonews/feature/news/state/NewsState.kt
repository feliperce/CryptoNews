package io.github.feliperce.cryptonews.feature.news.state

import androidx.compose.runtime.Immutable
import io.github.feliperce.cryptonews.feature.news.mapper.News

@Immutable
data class NewsUiState (
    val loading: Boolean = false,
    val news: News? = null
)

sealed class NewsIntent {
    data object GetNews : NewsIntent()
}

sealed interface NewsEffect {
    data class ShowError(val message: String) : NewsEffect
}