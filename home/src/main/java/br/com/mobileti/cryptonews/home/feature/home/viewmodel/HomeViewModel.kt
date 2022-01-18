package br.com.mobileti.cryptonews.home.feature.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.mobileti.cryptonews.data.Resource
import br.com.mobileti.cryptonews.home.extension.toFormattedDateString
import br.com.mobileti.cryptonews.home.feature.home.mapper.Article
import br.com.mobileti.cryptonews.home.feature.home.mapper.CurrentNews
import br.com.mobileti.cryptonews.home.feature.home.repository.HomeRepository
import br.com.mobileti.cryptonews.home.feature.home.state.HomeIntent
import br.com.mobileti.cryptonews.home.feature.home.state.HomeUiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val homeRepository: HomeRepository
) : ViewModel() {

    val intentChannel = Channel<HomeIntent>(Channel.UNLIMITED)

    private val _homeState = MutableStateFlow(HomeUiState(loading = false))
    val homeState: StateFlow<HomeUiState> = _homeState.asStateFlow()

    init {
        handleIntents()
    }

    fun sendIntent(intent: HomeIntent) {
        viewModelScope.launch {
            intentChannel.send(intent)
        }
    }

    private fun handleIntents() {
        intentChannel
            .consumeAsFlow()
            .onEach { intent ->
                when(intent) {
                    is HomeIntent.GetCurrentNews -> {
                        getCurrentNews(
                            oldFormatDate = intent.oldFormatDate,
                            newFormatDate = intent.newFormatDate
                        )
                    }
                    is HomeIntent.RefreshNews -> {
                        refreshNews(
                            oldFormatDate = intent.oldFormatDate,
                            newFormatDate = intent.newFormatDate
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun refreshNews(
        oldFormatDate: String,
        newFormatDate: String
    ) {
        viewModelScope.launch {
            homeRepository.refreshNews().collect { res ->
                when(res) {
                    is Resource.Loading -> {
                        _homeState.update {
                            it.copy(loading = res.isLoading)
                        }
                    }
                    is Resource.Error -> {
                        _homeState.update {
                            it.copy(error = res.error)
                        }
                    }
                    is Resource.Success -> {
                        val formattedArticleList = mapCurrentNewsList(
                            currentNews = res.data,
                            oldFormatDate = oldFormatDate,
                            newFormatDate = newFormatDate
                        )
                        _homeState.update {
                            it.copy(articleList = formattedArticleList)
                        }
                    }
                }
            }
        }
    }

    private fun getCurrentNews(
        oldFormatDate: String,
        newFormatDate: String
    ) {
        viewModelScope.launch {
            homeRepository.getCurrentNews()
                .collect { res ->
                when(res) {
                    is Resource.Loading -> {
                        _homeState.update {
                            it.copy(loading = res.isLoading)
                        }
                    }
                    is Resource.Error -> {
                        _homeState.update {
                            it.copy(error = res.error)
                        }
                    }
                    is Resource.Success -> {
                        val formattedArticleList = mapCurrentNewsList(
                            currentNews = res.data,
                            oldFormatDate = oldFormatDate,
                            newFormatDate = newFormatDate
                        )
                        _homeState.update {
                            it.copy(articleList = formattedArticleList)
                        }
                    }
                }
            }
        }
    }

    private fun mapCurrentNewsList(
        currentNews: List<CurrentNews>?,
        oldFormatDate: String,
        newFormatDate: String
    ): List<Article> {
        val articleList = currentNews?.lastOrNull()?.articles ?: listOf()
        return articleList.map {
            it.copy(
                publishedAt = it.publishedAt.toFormattedDateString(
                    oldFormat = oldFormatDate,
                    newFormat = newFormatDate
                )
            )
        }
    }
}