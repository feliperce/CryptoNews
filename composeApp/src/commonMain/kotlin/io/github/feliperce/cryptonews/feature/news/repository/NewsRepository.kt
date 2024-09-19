package io.github.feliperce.cryptonews.feature.news.repository

import io.github.feliperce.cryptonews.data.remote.NewsApi
import io.github.feliperce.cryptonews.data.remote.Resource
import io.github.feliperce.cryptonews.data.remote.response.ErrorResponse
import io.github.feliperce.cryptonews.data.remote.response.NewsResponse
import io.ktor.client.call.*
import io.ktor.http.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

class NewsRepository(
    private val newsApi: NewsApi
) {

    suspend fun getNews() = flow<Resource<NewsResponse, ErrorResponse>> {
        val response = newsApi.getNews()

        if (response.status == HttpStatusCode.OK) {
            emit(Resource.Success(data = response.body()))
        } else {
            emit(Resource.Error(error = response.body()))
        }
    }.onStart {
        emit(Resource.Loading(isLoading = true))
    }.onCompletion {
        emit(Resource.Loading(isLoading = false))
    }.catch {
        emit(Resource.Error(error = ErrorResponse(message = it.message ?: "Network error")))
    }

}