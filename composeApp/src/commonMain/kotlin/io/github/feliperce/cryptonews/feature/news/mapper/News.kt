package io.github.feliperce.cryptonews.feature.news.mapper

import io.github.feliperce.cryptonews.data.remote.response.ArticleItem
import io.github.feliperce.cryptonews.data.remote.response.NewsResponse
import kotlinx.serialization.Serializable

fun ArticleItem.toArticle() =
    Article(
        author = author ?: "",
        content = content ?: "",
        description = description ?: "",
        publishedAt = publishedAt ?: "",
        sourceName = source?.name ?: "",
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

@Serializable
data class Article(
    val author: String = "",
    val content: String = "",
    val description: String = "",
    val publishedAt: String = "",
    val sourceName: String = "",
    val title: String = "",
    val url: String = "",
    val urlToImage: String = ""
)
