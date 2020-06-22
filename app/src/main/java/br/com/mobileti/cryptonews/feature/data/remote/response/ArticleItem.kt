package br.com.mobileti.cryptonews.feature.data.remote.response

data class ArticleItem(
    val author: String? = null,
    val content: String? = null,
    val description: String? = null,
    val publishedAt: String? = null,
    val source: SourceItem? = null,
    val title: String? = null,
    val url: String? = null,
    val urlToImage: String? = null
)