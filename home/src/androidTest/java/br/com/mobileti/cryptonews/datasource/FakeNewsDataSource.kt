package br.com.mobileti.cryptonews.datasource

import br.com.mobileti.cryptonews.home.feature.home.mapper.Article

val fakeEmptyArticle = Article()

val fakeArticle = Article(
    author = "author",
    content = "content",
    description = "description",
    publishedAt = "publishedAt",
    title = "title",
    url = "url",
    urlToImage = "urlToImage"
)