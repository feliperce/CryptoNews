package br.com.mobileti.cryptonews.feature.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.mobileti.cryptonews.data.Resource
import br.com.mobileti.cryptonews.feature.home.repository.HomeRepository
import br.com.mobileti.cryptonews.feature.home.state.HomeIntent
import br.com.mobileti.cryptonews.feature.home.state.HomeUiState
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
                        getCurrentNews()
                    }
                    is HomeIntent.RefreshNews -> {
                        refreshNews()
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun refreshNews() {
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
                        _homeState.update {
                            it.copy(currentNews = res.data ?: listOf())
                        }
                    }
                }
            }
        }
    }

    private fun getCurrentNews() {
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
                        _homeState.update {
                            it.copy(currentNews = res.data ?: listOf())
                        }
                    }
                }
            }
        }
    }
}