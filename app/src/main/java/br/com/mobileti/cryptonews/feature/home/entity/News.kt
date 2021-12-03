package br.com.mobileti.cryptonews.feature.home.entity

data class Article(
    val articleId: Long,
    var newsId: Long,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val title: String,
    val url: String,
    val urlToImage: String,
)

data class News(
    val newsId: Long,
    val status: String,
    val totalResults: Int
)

data class CurrentNews(
    val news: News,
    val articles: List<Article>
)


