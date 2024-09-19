package io.github.feliperce.cryptonews.feature.news.repository

import io.github.feliperce.cryptonews.data.remote.NewsApi
import io.github.feliperce.cryptonews.data.remote.Resource
import io.github.feliperce.cryptonews.data.remote.mapper.ErrorData
import io.github.feliperce.cryptonews.data.remote.mapper.toErrorData
import io.github.feliperce.cryptonews.data.remote.response.ErrorResponse
import io.github.feliperce.cryptonews.data.remote.response.NewsResponse
import io.github.feliperce.cryptonews.feature.news.mapper.News
import io.github.feliperce.cryptonews.feature.news.mapper.toNews
import io.ktor.client.call.*
import io.ktor.http.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

class NewsRepository(
    private val newsApi: NewsApi
) {
    suspend fun getNews() = flow<Resource<News, ErrorData>> {
        val response = newsApi.getNews()

        if (response.status == HttpStatusCode.OK) {
            val news = response.body() as NewsResponse
            emit(Resource.Success(data = news.toNews()))
        } else {
            val errorResponse = response.body() as ErrorResponse
            emit(Resource.Error(error = errorResponse.toErrorData()))
        }
    }.onStart {
        emit(Resource.Loading(isLoading = true))
    }.onCompletion {
        emit(Resource.Loading(isLoading = false))
    }.catch {
        emit(Resource.Error(error = ErrorResponse(message = it.message ?: "Network error").toErrorData()))
    }
}
