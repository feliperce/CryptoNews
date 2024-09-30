package io.github.feliperce.cryptonews.feature.news.mapper

import io.github.feliperce.cryptonews.data.remote.response.ArticleItem
import io.github.feliperce.cryptonews.data.remote.response.NewsResponse
import io.github.feliperce.cryptonews.data.remote.response.SourceItem


fun SourceItem.toSource() =
    Source(
        id = id ?: "",
        name = name ?: ""
    )

fun ArticleItem.toArticle() =
    Article(
        author = author ?: "",
        content = content ?: "",
        description = description ?: "",
        publishedAt = publishedAt ?: "",
        source = source?.toSource() ?: Source(),
        title = title ?: "",
        url = url ?: "",
        urlToImage = urlToImage ?: ""
    )

fun List<ArticleItem>.toArticleList() =
    map {
        it.toArticle()
    }

fun NewsResponse.toNews() =
    News(
        articles = articles?.toArticleList() ?: emptyList(),
        status = status ?: "",
        totalResults = totalResults ?: 0
    )

data class News(
    val articles: List<Article> = emptyList(),
    val status: String = "",
    val totalResults: Int = 0
)

data class Article(
    val author: String = "",
    val content: String = "",
    val description: String = "",
    val publishedAt: String = "",
    val source: Source = Source(),
    val title: String = "",
    val url: String = "",
    val urlToImage: String = ""
)

data class Source(
    val id: String = "",
    val name: String = ""
)
