package br.com.mobileti.cryptonews.data.model

data class News(
    val articles: List<Article>? = arrayListOf(),
    val status: String? = null,
    val totalResults: Int? = null
)