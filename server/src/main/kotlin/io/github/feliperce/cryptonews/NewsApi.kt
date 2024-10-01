package io.github.feliperce.cryptonews

import io.github.feliperce.cryptonews.server.BuildConfig
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class NewsApi(
    private val client: HttpClient
) {

    suspend fun getNews(): HttpResponse {
        return client.get {
            url("$BASE_URL/everything?q=cryptocurrency&sortBy=publishedAt&apiKey=${BuildConfig.API_KEY}")
        }
    }

    companion object {
        const val BASE_URL = "https://newsapi.org/v2"
    }
}