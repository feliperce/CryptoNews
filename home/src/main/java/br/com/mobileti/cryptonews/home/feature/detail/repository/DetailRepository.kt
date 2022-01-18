package br.com.mobileti.cryptonews.home.feature.detail.repository

import br.com.mobileti.cryptonews.data.Resource
import br.com.mobileti.cryptonews.data.local.dao.NewsDao
import br.com.mobileti.cryptonews.data.extension.callLocalData
import br.com.mobileti.cryptonews.home.feature.home.mapper.Article
import br.com.mobileti.cryptonews.home.feature.home.mapper.toArticle
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class DetailRepository(
    private val newsDao: NewsDao
) {

    fun getArticleById(articleId: Long) = flow<Resource<Article?>> {
        val sync = callLocalData(
            local = { newsDao.getArticleByArticleId(articleId) },
            mapper = { it?.toArticle() }
        )
        emitAll(sync)
    }

}