package br.com.mobileti.cryptonews.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class Article(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val author: String? = null,
    val content: String? = null,
    val description: String? = null,
    @ColumnInfo(name = "published_at") val publishedAt: String? = null,
    val title: String? = null,
    val url: String? = null,
    @ColumnInfo(name = "image_url") val urlToImage: String? = null
)