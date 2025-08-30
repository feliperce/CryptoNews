package io.github.feliperce.cryptonews.feature.news.repository

import io.github.feliperce.cryptonews.data.remote.NewsApi
import io.github.feliperce.cryptonews.data.remote.Resource
import io.github.feliperce.cryptonews.data.remote.mapper.ErrorData
import io.github.feliperce.cryptonews.feature.news.mapper.News
import io.github.feliperce.cryptonews.feature.news.mapper.toNews
import io.github.feliperce.cryptonews.data.remote.response.NewsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class NewsRepositoryTest {

    private val json = Json { ignoreUnknownKeys = true }

    private fun clientWithResponse(status: HttpStatusCode, body: String): HttpClient {
        return HttpClient(MockEngine) {
            install(ContentNegotiation) { json(json) }
            engine {
                addHandler { _ ->
                    respond(
                        content = body,
                        status = status,
                        headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                    )
                }
            }
        }
    }

    @Test
    fun `GIVEN success response WHEN getNews THEN emits loading success loadingFalse`() = runTest {
        val newsResponse = NewsResponse(
            status = "ok",
            totalResults = 1,
            articles = emptyList()
        )
        val client = clientWithResponse(HttpStatusCode.OK, json.encodeToString(newsResponse))
        val api = NewsApi(client)
        val repo = NewsRepository(api)

        val emissions = repo.getNews().toList()

        assertIs<Resource.Loading<*, *>>(emissions[0])
        assertTrue((emissions[0] as Resource.Loading<*, *>).isLoading)

        assertIs<Resource.Success<News, ErrorData>>(emissions[1])
        val success = emissions[1] as Resource.Success<News, ErrorData>
        assertEquals(newsResponse.toNews(), success.data)

        assertIs<Resource.Loading<*, *>>(emissions[2])
        assertTrue(!(emissions[2] as Resource.Loading<*, *>).isLoading)
    }

    @Test
    fun `GIVEN error response WHEN getNews THEN emits loading error loadingFalse`() = runTest {
        val errJson = """{"status":"error","code":"badRequest","message":"Invalid"}"""
        val client = clientWithResponse(HttpStatusCode.BadRequest, errJson)
        val api = NewsApi(client)
        val repo = NewsRepository(api)

        val emissions = repo.getNews().toList()

        assertIs<Resource.Loading<*, *>>(emissions[0])
        val error = emissions[1] as Resource.Error<News, ErrorData>
        assertEquals("Invalid", error.error?.message)
        assertIs<Resource.Loading<*, *>>(emissions[2])
        assertTrue(!(emissions[2] as Resource.Loading<*, *>).isLoading)
    }

    @Test
    fun `GIVEN exception WHEN getNews THEN emits loading error with message and loadingFalse`() = runTest {
        val client = HttpClient(MockEngine) {
            install(ContentNegotiation) { json(json) }
            engine {
                addHandler { _ -> throw RuntimeException("boom") }
            }
        }
        val api = NewsApi(client)
        val repo = NewsRepository(api)

        val emissions = repo.getNews().toList()

        assertIs<Resource.Loading<*, *>>(emissions[0])
        val error = emissions[1] as Resource.Error<News, ErrorData>
        assertEquals("boom", error.error?.message)
        assertIs<Resource.Loading<*, *>>(emissions[2])
        assertTrue(!(emissions[2] as Resource.Loading<*, *>).isLoading)
    }
}
