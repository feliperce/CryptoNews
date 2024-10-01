package io.github.feliperce.cryptonews

import io.github.feliperce.cryptonews.data.remote.Resource
import io.github.feliperce.cryptonews.data.remote.response.ErrorResponse
import io.github.feliperce.cryptonews.data.remote.response.NewsResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.jetty.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json

fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = SERVER_HOST, module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }

    val client = HttpClient(Jetty) {
        install(Logging) {
            level = LogLevel.ALL
        }
        install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
        defaultRequest {
            header("Content-Type", ContentType.Application.Json)
        }
        expectSuccess = true
    }

    val newsApi = NewsApi(client)

    routing {
        get("/getNews") {
            val response = newsApi.getNews()

            val resource = if (response.status == HttpStatusCode.OK) {
                val news = response.body() as NewsResponse
                Resource.Success<NewsResponse, ErrorResponse>(data = news)
            } else {
                val errorResponse = response.body() as ErrorResponse
                Resource.Error<NewsResponse, ErrorResponse>(error = errorResponse)
            }

            if (resource is Resource.Success) {
                resource.data?.let { data ->
                    call.respond(data)
                }
            } else {
                call.respond(resource.error!!)
            }
        }
    }
}