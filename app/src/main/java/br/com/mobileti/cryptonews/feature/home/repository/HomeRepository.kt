package br.com.mobileti.cryptonews.feature.home.repository

import br.com.mobileti.cryptonews.data.Resource
import br.com.mobileti.cryptonews.data.local.dao.NewsDao
import br.com.mobileti.cryptonews.data.local.entity.NewsWithArticles
import br.com.mobileti.cryptonews.data.remote.mapper.toArticleEntityList
import br.com.mobileti.cryptonews.data.remote.mapper.toNewsEntity
import br.com.mobileti.cryptonews.data.remote.service.NewsService
import br.com.mobileti.cryptonews.extension.syncData
import br.com.mobileti.cryptonews.feature.home.mapper.toCurrentNewsList
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.transform

class HomeRepository(
    private val newsDao: NewsDao,
    private val newsService: NewsService
) {

    fun getCurrentNews() = flow<Resource<List<NewsWithArticles>>> {
        val sync = syncData(
            local = { newsDao.getNewsWithArticle() },
            remote = { newsService.getCurrentNews() },
            onRemote = {
                newsDao.insertNewsWithArticles(
                    it.toNewsEntity(),
                    it.articles?.toArticleEntityList() ?: arrayListOf()
                )
            },
            shouldFetchFromRemote = { it?.isEmpty() ?: true }
        )

        emitAll(sync)

    }.transform {
        if (it is Resource.Success) {
            emit(Resource.Success(it.data?.toCurrentNewsList()))
        } else {
            emit(it)
        }
    }

}