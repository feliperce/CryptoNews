package br.com.mobileti.cryptonews.data.remote.mapper

import br.com.mobileti.cryptonews.data.local.entity.ArticleEntity
import br.com.mobileti.cryptonews.data.local.entity.NewsEntity
import br.com.mobileti.cryptonews.data.local.entity.SourceEntity
import br.com.mobileti.cryptonews.data.remote.response.ArticleResponse
import br.com.mobileti.cryptonews.data.remote.response.NewsResponse
import br.com.mobileti.cryptonews.data.remote.response.SourceResponse

fun NewsResponse.toNewsEntity() =
    NewsEntity(
        status = status ?: "",
        totalResults = totalResults ?: 0
    )
fun ArticleResponse.toArticleEntity() =
    ArticleEntity(
        author = author ?: "",
        content = content ?: "",
        description = description ?: "",
        publishedAt = publishedAt ?: "",
        title = title ?: "",
        url = url ?: "",
        urlToImage = urlToImage ?: "",
        source = source?.toSourceEntity()
    )

fun List<ArticleResponse>.toArticleEntityList() =
    map {
        it.toArticleEntity()
    } ?: arrayListOf()

fun SourceResponse.toSourceEntity() =
    SourceEntity(
        id = id ?: "",
        name = name ?: ""
    )