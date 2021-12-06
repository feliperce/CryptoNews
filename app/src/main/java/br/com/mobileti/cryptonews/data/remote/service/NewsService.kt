package br.com.mobileti.cryptonews.data.remote.service

import br.com.mobileti.cryptonews.BuildConfig
import br.com.mobileti.cryptonews.data.remote.response.NewsResponse
import retrofit2.Response
import retrofit2.http.GET

interface NewsService {

    @GET("https://newsapi.org/v2/everything?apiKey=${BuildConfig.API_KEY}&q=Cryptocurrency")
    suspend fun getCurrentNews(): Response<NewsResponse>

}