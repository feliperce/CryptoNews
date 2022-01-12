package br.com.mobileti.cryptonews.feature.home.mapper

data class Article(
    val articleId: Long = 0,
    var newsId: Long = 0,
    val author: String = "",
    val content: String = "",
    val description: String = "",
    val publishedAt: String = "",
    val title: String = "",
    val url: String = "",
    val urlToImage: String = ""
)

data class News(
    val newsId: Long = 0,
    val status: String = "",
    val totalResults: Int = 0
)

data class CurrentNews(
    val news: News = News(),
    val articles: List<Article> = listOf()
)


