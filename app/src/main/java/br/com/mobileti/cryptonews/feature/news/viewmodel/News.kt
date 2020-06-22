package br.com.mobileti.cryptonews.feature.news.viewmodel

data class News(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)