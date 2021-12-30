package br.com.mobileti.cryptonews.feature.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.mobileti.cryptonews.data.Resource
import br.com.mobileti.cryptonews.feature.detail.repository.DetailRepository
import br.com.mobileti.cryptonews.feature.detail.state.DetailIntent
import br.com.mobileti.cryptonews.feature.detail.state.DetailUiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DetailViewModel(
    private val detailRepository: DetailRepository
) : ViewModel() {

    private val intentChannel = Channel<DetailIntent>(Channel.UNLIMITED)

    private val _detailState = MutableStateFlow(DetailUiState(loading = false))
    val detailState: StateFlow<DetailUiState> = _detailState.asStateFlow()

    init {
        handleIntents()
    }

    fun sendIntent(intent: DetailIntent) {
        viewModelScope.launch {
            intentChannel.send(intent)
        }
    }

    private fun handleIntents() {
        intentChannel
            .consumeAsFlow()
            .onEach { intent ->
                when(intent) {
                    is DetailIntent.GetArticleById -> {
                        getArticleByArticleId(articleId = intent.articleId)
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun getArticleByArticleId(articleId: Long) {
        viewModelScope.launch {
            detailRepository.getArticleById(articleId = articleId).collect { res ->
                when(res) {
                    is Resource.Loading -> {
                        _detailState.update {
                            it.copy(loading = res.isLoading)
                        }
                    }
                    is Resource.Error -> {
                        _detailState.update {
                            it.copy(error = res.error)
                        }
                    }
                    is Resource.Success -> {
                        res.data?.let { data ->
                            _detailState.update {
                                it.copy(article = data)
                            }
                        }
                    }
                }
            }
        }
    }

}