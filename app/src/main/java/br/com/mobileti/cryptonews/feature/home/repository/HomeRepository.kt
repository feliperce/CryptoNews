package br.com.mobileti.cryptonews.feature.home.repository

import br.com.mobileti.cryptonews.data.Resource
import br.com.mobileti.cryptonews.data.local.db.NewsDb
import br.com.mobileti.cryptonews.data.remote.service.NewsService
import br.com.mobileti.cryptonews.extension.syncData
import br.com.mobileti.cryptonews.feature.home.entity.CurrentNews
import kotlinx.coroutines.flow.flow
import java.util.concurrent.Flow

class HomeRepository(
    private val db: NewsDb,
    private val newsService: NewsService
) {

    fun getCurrentNews() = flow<Resource<List<CurrentNews>>> {
        syncData(
            local = { db.newsDao().getNewsWithArticle() },
            remote = { newsService.getCurrentNews() },
            onRemote = { db.newsDao().insertNewsWithArticles(it) }
        )
    }

}