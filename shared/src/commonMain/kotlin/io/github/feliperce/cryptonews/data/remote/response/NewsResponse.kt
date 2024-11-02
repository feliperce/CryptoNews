package io.github.feliperce.cryptonews.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(
    val articles: List<ArticleItem>? = null,
    val status: String? = null,
    val totalResults: Int? = null
)

@Serializable
data class ArticleItem(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: SourceItem?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
)

@Serializable
data class SourceItem(
    val id: String?,
    val name: String?
)