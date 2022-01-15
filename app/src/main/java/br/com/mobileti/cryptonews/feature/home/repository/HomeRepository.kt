package br.com.mobileti.cryptonews.feature.home.repository

import br.com.mobileti.cryptonews.data.Resource
import br.com.mobileti.cryptonews.data.local.dao.NewsDao
import br.com.mobileti.cryptonews.data.remote.mapper.toArticleEntityList
import br.com.mobileti.cryptonews.data.remote.mapper.toNewsEntity
import br.com.mobileti.cryptonews.data.remote.service.NewsService
import br.com.mobileti.cryptonews.data.extension.syncData
import br.com.mobileti.cryptonews.feature.home.mapper.CurrentNews
import br.com.mobileti.cryptonews.feature.home.mapper.toCurrentNewsList
import kotlinx.coroutines.flow.*

class HomeRepository(
    private val newsDao: NewsDao,
    private val newsService: NewsService
) {

    fun getCurrentNews() = flow<Resource<List<CurrentNews>>> {
        val sync = syncData(
            local = { newsDao.getNewsWithArticle() },
            remote = { newsService.getCurrentNews() },
            mapper = { it?.toCurrentNewsList().orEmpty() },
            onRemote = {
                newsDao.insertNewsWithArticles(
                    it.toNewsEntity(),
                    it.articles?.toArticleEntityList() ?: arrayListOf()
                )
            },
            shouldFetchFromRemote = { it?.isEmpty() ?: true }
        )
        emitAll(sync)
    }

    fun refreshNews() = flow<Resource<List<CurrentNews>>> {
        val sync = syncData(
            local = { newsDao.getNewsWithArticle() },
            remote = { newsService.getCurrentNews() },
            mapper = { it?.toCurrentNewsList().orEmpty() },
            onRemote = {
                newsDao.insertNewsWithArticles(
                    it.toNewsEntity(),
                    it.articles?.toArticleEntityList() ?: arrayListOf()
                )
            },
            shouldFetchFromRemote = { true }
        ).onStart {
            newsDao.removeNewsCache()
        }
        emitAll(sync)
    }

}