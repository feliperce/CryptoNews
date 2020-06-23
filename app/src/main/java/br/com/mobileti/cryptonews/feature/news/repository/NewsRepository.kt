package br.com.mobileti.cryptonews.feature.news.repository

import br.com.mobileti.cryptonews.data.handler.Resource
import br.com.mobileti.cryptonews.data.model.News
import br.com.mobileti.cryptonews.data.remote.service.NewsService
import br.com.mobileti.cryptonews.extension.networkCall
import kotlinx.coroutines.flow.*
import retrofit2.Retrofit

class NewsRepository(
    private val retrofit: Retrofit,
    private val newsService: NewsService
) {

    fun getNews(apiKey: String): Flow<Resource<News>> = flow {
        emit(Resource.loading(true))
        emit(retrofit.networkCall { newsService.getNews(apiKey)})
    }
}