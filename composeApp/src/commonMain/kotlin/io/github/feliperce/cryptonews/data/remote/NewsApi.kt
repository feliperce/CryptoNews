package io.github.feliperce.cryptonews.data.remote

import io.github.feliperce.cryptonews.SERVER_BASE_URL
import io.github.feliperce.cryptonews.SERVER_HOST
import io.github.feliperce.cryptonews.SERVER_PORT
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
            url("$SERVER_BASE_URL/getNews")
        }
    }
}