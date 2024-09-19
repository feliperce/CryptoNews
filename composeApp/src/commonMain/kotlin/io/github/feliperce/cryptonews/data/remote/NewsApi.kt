package io.github.feliperce.cryptonews.data.remote

import CryptoNews.composeApp.BuildConfig
import io.github.feliperce.cryptonews.data.remote.response.NewsResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class NewsApi (
    private val client: HttpClient
) {
    suspend fun getNews(): HttpResponse {
        return client.get {
            contentType(ContentType.Application.Json)
            url("$BASE_URL/everything?q=cryptocurrency&sortBy=publishedAt&apiKey=${BuildConfig.API_KEY}")
        }
    }

    companion object {
        const val BASE_URL = "https://newsapi.org/v2"
    }
}