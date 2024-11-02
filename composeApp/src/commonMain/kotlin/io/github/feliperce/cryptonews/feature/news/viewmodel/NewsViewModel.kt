package io.github.feliperce.cryptonews.feature.news.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.aakira.napier.Napier
import io.github.feliperce.cryptonews.data.remote.Resource
import io.github.feliperce.cryptonews.feature.news.repository.NewsRepository
import io.github.feliperce.cryptonews.feature.news.state.NewsIntent
import io.github.feliperce.cryptonews.feature.news.state.NewsUiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {
    private val intentChannel = Channel<NewsIntent>(Channel.UNLIMITED)

    private val _newsState = MutableStateFlow(NewsUiState(loading = false))
    val newsState: StateFlow<NewsUiState> = _newsState.asStateFlow()

    init {
        handleIntents()
    }

    fun sendIntent(intent: NewsIntent) {
        viewModelScope.launch {
            intentChannel.send(intent)
        }
    }

    private fun handleIntents() {
        intentChannel
            .consumeAsFlow()
            .onEach { intent ->
                when(intent) {
                    is NewsIntent.GetNews -> {
                        getNews()
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun getNews() {
        viewModelScope.launch {
            newsRepository.getNews().collect { res ->
                when(res) {
                    is Resource.Success -> {
                        _newsState.update {
                            it.copy(news = res.data)
                        }
                    }
                    is Resource.Error -> {
                        Napier.e("ERROR ${res.error?.code}: ${res.error?.message}")
                        _newsState.update {
                            it.copy(errorData = res.error)
                        }
                    }
                    is Resource.Loading -> {
                        _newsState.update {
                            it.copy(loading = res.isLoading)
                        }
                    }
                }
            }
        }
    }

}