package io.github.feliperce.cryptonews.data.remote.response

data class NewsResponse(
    val articles: List<ArticleItem>?,
    val status: String?,
    val totalResults: Int?
)

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

data class SourceItem(
    val id: String?,
    val name: String?
)