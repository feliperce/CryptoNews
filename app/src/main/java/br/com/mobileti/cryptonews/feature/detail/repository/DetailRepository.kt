package br.com.mobileti.cryptonews.feature.detail.repository

import br.com.mobileti.cryptonews.data.Resource
import br.com.mobileti.cryptonews.data.local.dao.NewsDao
import br.com.mobileti.cryptonews.data.local.entity.ArticleEntity
import br.com.mobileti.cryptonews.data.remote.mapper.toArticleEntityList
import br.com.mobileti.cryptonews.data.remote.mapper.toNewsEntity
import br.com.mobileti.cryptonews.extension.callLocalData
import br.com.mobileti.cryptonews.extension.syncData
import br.com.mobileti.cryptonews.feature.home.mapper.Article
import br.com.mobileti.cryptonews.feature.home.mapper.CurrentNews
import br.com.mobileti.cryptonews.feature.home.mapper.toArticle
import br.com.mobileti.cryptonews.feature.home.mapper.toCurrentNewsList
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