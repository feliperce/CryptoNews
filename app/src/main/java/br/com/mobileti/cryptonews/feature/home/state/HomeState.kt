package br.com.mobileti.cryptonews.feature.home.state

import br.com.mobileti.cryptonews.feature.home.mapper.CurrentNews
import java.lang.Exception

data class HomeUiState (
    val loading: Boolean = false,
    val error: Exception? = null,
    val currentNews: List<CurrentNews> = listOf()
)

sealed class HomeIntent {
    object GetCurrentNews : HomeIntent()
}