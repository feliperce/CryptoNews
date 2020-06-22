package br.com.mobileti.cryptonews.feature.data.remote.response

data class NewsResponse(
    val articles: List<ArticleItem>? = arrayListOf(),
    val status: String? = null,
    val totalResults: Int? = null
)