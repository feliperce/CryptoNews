package br.com.mobileti.cryptonews.feature.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.mobileti.cryptonews.feature.detail.repository.DetailRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class DetailViewModel(
    private val detailRepository: DetailRepository
) : ViewModel() {

    val intentChannel = Channel<String>(Channel.UNLIMITED)

    init {
        handleIntents()
    }

    private fun handleIntents() {
        intentChannel
            .consumeAsFlow()
            .onEach { intent ->
                when(intent) {
                    /*is HomeIntent.GetCurrentNews -> {
                        getCurrentNews()
                    }*/
                }
            }.launchIn(viewModelScope)
    }

}