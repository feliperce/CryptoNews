package br.com.mobileti.cryptonews.feature.news.repository

import br.com.mobileti.cryptonews.data.handler.Resource
import br.com.mobileti.cryptonews.data.remote.response.NewsResponse
import br.com.mobileti.cryptonews.data.remote.service.NewsService
import br.com.mobileti.cryptonews.extension.networkCall
import br.com.mobileti.cryptonews.feature.news.viewmodel.News
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.Retrofit

class NewsRepository(
    private val retrofit: Retrofit,
    private val newsService: NewsService
) {

    fun teste1(): Flow<NewsResponse> = flow {
        emit(NewsResponse())
    }

    fun teste2(): Flow<String> = flow {
        emit("dsadasd")
    }

    fun testeMerge(): Flow<News> = flow {
        emit(6)
    }.combine(teste1()) { number, letter ->
        News(arrayListOf(), ",", 0)
    }

    fun syncNews(apiKey: String): Flow<Resource<News>> = flow {

    }

    fun getNews(apiKey: String): Flow<Resource<NewsResponse>> = flow {
        emit(retrofit.networkCall { newsService.getNews(apiKey) })
    }
}