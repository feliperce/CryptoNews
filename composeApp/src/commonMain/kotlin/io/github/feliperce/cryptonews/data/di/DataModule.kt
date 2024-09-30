package io.github.feliperce.cryptonews.data.di

import io.github.feliperce.cryptonews.data.remote.NewsApi
import io.github.feliperce.cryptonews.data.remote.response.ErrorResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val dataModule = module {
    single {
        HttpClient {
            install(Logging) {
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
            defaultRequest {
                header("Content-Type", ContentType.Application.Json)
            }
            expectSuccess = true
        }
    }

    single { NewsApi(get()) }
}