package io.github.feliperce.cryptonews.feature.news.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.aakira.napier.Napier
import io.github.feliperce.cryptonews.data.remote.Resource
import io.github.feliperce.cryptonews.feature.news.repository.NewsRepository
import io.github.feliperce.cryptonews.feature.news.state.NewsEffect
import io.github.feliperce.cryptonews.feature.news.state.NewsIntent
import io.github.feliperce.cryptonews.feature.news.state.NewsUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {
    private val _intents = MutableSharedFlow<NewsIntent>(extraBufferCapacity = 64)
    private val intents: SharedFlow<NewsIntent> = _intents

    private val _newsState = MutableStateFlow(NewsUiState(loading = false))
    val newsState: StateFlow<NewsUiState> = _newsState.asStateFlow()

    private val _effects = MutableSharedFlow<NewsEffect>(extraBufferCapacity = 16)
    val effects: SharedFlow<NewsEffect> = _effects

    private var getNewsJob: Job? = null

    init {
        handleIntents()
    }

    fun sendIntent(intent: NewsIntent) {
        _intents.tryEmit(intent)
    }

    private fun handleIntents() {
        intents
            .onEach { intent ->
                when (intent) {
                    is NewsIntent.GetNews -> getNews()
                }
            }
            .launchIn(viewModelScope)
    }

    private fun getNews() {
        getNewsJob?.cancel()
        getNewsJob = viewModelScope.launch {
            newsRepository.getNews().collect { res ->
                when (res) {
                    is Resource.Success -> {
                        _newsState.update { it.copy(loading = false, news = res.data) }
                    }
                    is Resource.Error -> {
                        val msg = res.error?.message ?: "Network error"
                        Napier.e("ERROR: $msg")
                        _effects.tryEmit(NewsEffect.ShowError(msg))
                        _newsState.update { it.copy(loading = false) }
                    }
                    is Resource.Loading -> {
                        _newsState.update { it.copy(loading = res.isLoading) }
                    }
                }
            }
        }
    }
}