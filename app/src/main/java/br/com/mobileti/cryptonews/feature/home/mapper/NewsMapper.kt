package br.com.mobileti.cryptonews.feature.home.mapper

import br.com.mobileti.cryptonews.data.local.entity.ArticleEntity
import br.com.mobileti.cryptonews.data.local.entity.NewsEntity
import br.com.mobileti.cryptonews.data.local.entity.NewsWithArticles
import br.com.mobileti.cryptonews.feature.home.entity.Article
import br.com.mobileti.cryptonews.feature.home.entity.CurrentNews
import br.com.mobileti.cryptonews.feature.home.entity.News

fun ArticleEntity.toArticle() =
    Article(
        articleId = articleId,
        newsId = newsId ?: 0,
        author = author ?: "",
        content = content ?: "",
        description = description ?: "",
        publishedAt = publishedAt ?: "",
        title = title ?: "",
        url = url ?: "",
        urlToImage = urlToImage ?: ""
    )

fun List<ArticleEntity>.toArticleList() =
    map {
        it.toArticle()
    }

fun NewsEntity.toNews() =
    News(
        newsId = newsId,
        status = status ?: "",
        totalResults = totalResults ?: 0
    )

fun NewsWithArticles.toCurrentNews() =
    CurrentNews(
        news = news.toNews(),
        articles = articles.toArticleList()
    )