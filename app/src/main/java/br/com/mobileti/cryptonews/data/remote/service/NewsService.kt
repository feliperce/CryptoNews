package br.com.mobileti.cryptonews.data.remote.service

import br.com.mobileti.cryptonews.data.model.News
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    companion object {
        const val NEWS_PATH = "everything?q=cryptocurrency"
    }

    @GET(NEWS_PATH)
    suspend fun getNews(
        @Query("apiKey") apiKey: String
    ): Response<News>

}
