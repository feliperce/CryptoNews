package br.com.mobileti.cryptonews.feature.news.repository

import br.com.mobileti.cryptonews.data.handler.Resource
import br.com.mobileti.cryptonews.data.handler.Status
import br.com.mobileti.cryptonews.data.local.db.Database
import br.com.mobileti.cryptonews.data.model.Article
import br.com.mobileti.cryptonews.data.remote.service.NewsService
import br.com.mobileti.cryptonews.extension.callResourceData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.Retrofit

class NewsRepository(
    private val newsService: NewsService,
    private val db: Database
) {

    fun getCachedNews(apiKey: String): Flow<Resource<List<Article>>> = flow {

        emitAll(callResourceData(
            local = { db.newsDao().getNews() },
            remote = { newsService.getNews(apiKey) },
            shouldFetchFromRemote = { it.isNullOrEmpty() },
            onRemoteResource = { Resource.writingDb(it?.articles) }
        ))

    }.flowOn(Dispatchers.Default)
     .onStart {
        emit(Resource.loading(true))
    }.onCompletion {
        emit(Resource.loading(false))
    }.flowOn(Dispatchers.Default)
    .transform {
        if (it.status == Status.WritingDb && it.data != null) {
            db.newsDao().insertNews(it.data)
            emit(Resource.success(it.data))
        } else {
            emit(it)
        }
    }.flowOn(Dispatchers.IO)

    fun syncNews(apiKey: String): Flow<Resource<List<Article>>> = flow {

        emitAll(callResourceData(
            local = { emptyList<Article>() },
            remote = { newsService.getNews(apiKey) },
            onRemoteResource = { Resource.writingDb(it?.articles) }
        ))

    }.onStart {
        emit(Resource.loading(true))
    }.onCompletion {
        emit(Resource.loading(false))
    }.flowOn(Dispatchers.Default)
    .transform {
        if (it.status == Status.WritingDb && it.data != null) {
            db.newsDao().deleteAllNews()
            db.newsDao().insertNews(it.data)
            emit(Resource.success(it.data))
        } else {
            emit(it)
        }
    }.flowOn(Dispatchers.IO)

}