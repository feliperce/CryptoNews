package br.com.mobileti.cryptonews.feature.news.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.mobileti.cryptonews.BuildConfig
import br.com.mobileti.cryptonews.R
import br.com.mobileti.cryptonews.data.handler.Resource
import br.com.mobileti.cryptonews.data.handler.Status
import br.com.mobileti.cryptonews.data.model.Article
import br.com.mobileti.cryptonews.feature.news.repository.NewsRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class NewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val TAG = NewsViewModel::class.java.simpleName

    private val _errorHandlerLiveData = MutableLiveData<Int>()
    val errorHandlerLiveData: LiveData<Int> = _errorHandlerLiveData

    private val _dataLoadingLiveData = MutableLiveData<Boolean>()
    val dataLoadingLiveData: LiveData<Boolean> = _dataLoadingLiveData

    private val _newsLiveData = MutableLiveData<List<Article>>()
    val newsLiveData: LiveData<List<Article>> = _newsLiveData

    init {
        if (hasApiKey()) {
            getCachedNews()
        } else {
            _errorHandlerLiveData.postValue(R.string.error_empty_api)
        }
    }

    fun syncNews() {
        viewModelScope.launch {

            newsRepository.syncNews(BuildConfig.API_KEY).collect {
                newsCollect(it)
            }
        }
    }

    private fun hasApiKey(): Boolean {
        return BuildConfig.API_KEY != "ENTER_KEY"
    }

    private fun getCachedNews() {
        viewModelScope.launch {

            newsRepository.getCachedNews(BuildConfig.API_KEY).collect {
                newsCollect(it)
            }
        }
    }

    private fun newsCollect(newsResource: Resource<List<Article>>) {
        newsResource.let {
            when (it.status) {
                is Status.Loading -> {
                    _dataLoadingLiveData.postValue(it.status.isLoading)
                }
                is Status.Error -> {
                    _errorHandlerLiveData.postValue(it.status.exception?.msg)
                }
                is Status.Success -> {
                    _newsLiveData.postValue(it.data ?: emptyList())
                }
            }
        }
    }

}
