package br.com.mobileti.cryptonews.data.remote.service

import br.com.mobileti.cryptonews.data.remote.response.NewsResponse
import br.com.mobileti.cryptonews.feature.news.viewmodel.News
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    companion object {
        const val NEWS_PATH = "everything?q=cryptocurrency"
    }

    @GET
    suspend fun getNews(
        @Query("apiKey") apiKey: String
    ): Response<NewsResponse>

    @GET
    suspend fun getNewss(
        @Query("apiKey") apiKey: String
    ): Response<News>

}
