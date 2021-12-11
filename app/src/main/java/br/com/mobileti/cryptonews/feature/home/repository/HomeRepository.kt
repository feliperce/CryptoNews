package br.com.mobileti.cryptonews.feature.home.repository

import br.com.mobileti.cryptonews.data.Resource
import br.com.mobileti.cryptonews.data.Status
import br.com.mobileti.cryptonews.data.local.db.NewsDb
import br.com.mobileti.cryptonews.data.local.entity.NewsWithArticles
import br.com.mobileti.cryptonews.data.remote.mapper.toArticleEntityList
import br.com.mobileti.cryptonews.data.remote.mapper.toNewsEntity
import br.com.mobileti.cryptonews.data.remote.service.NewsService
import br.com.mobileti.cryptonews.extension.callResourceData
import br.com.mobileti.cryptonews.extension.syncData
import br.com.mobileti.cryptonews.feature.home.entity.CurrentNews
import br.com.mobileti.cryptonews.feature.home.mapper.toCurrentNewsList
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import okhttp3.internal.wait
import java.util.concurrent.Flow

class HomeRepository(
    private val db: NewsDb,
    private val newsService: NewsService
) {

    fun getCurrentNews() = flow<Resource<List<NewsWithArticles>>> {
        val sync = syncData(
            local = { db.newsDao().getNewsWithArticle() },
            remote = { newsService.getCurrentNews() },
            onRemote = {
                db.newsDao().insertNewsWithArticles(
                    it.toNewsEntity(),
                    it.articles?.toArticleEntityList() ?: arrayListOf()
                )
            },
            shouldFetchFromRemote = { it?.isEmpty() ?: true }
        )

        emitAll(sync)

    }.transform {
        if (it.status == Status.Success) {
            emit(Resource.success(it.data?.toCurrentNewsList()))
        } else {
            emit(it)
        }
    }

}