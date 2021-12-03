package br.com.mobileti.cryptonews.data.local.entity

import androidx.room.*

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true) val articleId: Long = 0,
    var newsId: Long? = -1,
    val author: String?,
    val content: String?,
    val description: String?,
    @ColumnInfo(name = "published_at") val publishedAt: String?,
    val title: String?,
    val url: String?,
    @ColumnInfo(name = "image_url") val urlToImage: String?,
    @Embedded val source: SourceEntity
)

@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey(autoGenerate = true) val newsId: Long = 0,
    //val articles: List<ArticleEntity>?,
    val status: String?,
    val totalResults: Int?
)

/*
@Entity(tableName = "articles")
data class ArticleEntity(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: SourceEntity?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
)
*/

data class SourceEntity(
    val id: String?,
    val name: String?
)

data class NewsWithArticles(
    @Embedded val news: NewsEntity,
    @Relation(
        parentColumn = "newsId",
        entityColumn = "articleId"
    )
    val articles: List<ArticleEntity>
)