package br.com.mobileti.cryptonews.data.local.dao

import androidx.room.*
import br.com.mobileti.cryptonews.data.local.entity.ArticleEntity
import br.com.mobileti.cryptonews.data.local.entity.NewsEntity
import br.com.mobileti.cryptonews.data.local.entity.NewsWithArticles

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(newsEntity: NewsEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(articleEntity: ArticleEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articleEntityList: List<ArticleEntity>)

    @Transaction
    suspend fun insertNewsWithArticles(
        newsEntity: NewsEntity,
        articleEntityList: List<ArticleEntity>
    ) {
        val newsId = insertNews(newsEntity)

        articleEntityList.forEach {
            it.newsId = newsId
        }
        insertArticles(articleEntityList)
    }

    @Query("SELECT * from articles WHERE articles.articleId = :articleId")
    suspend fun getArticleByArticleId(articleId: Long): ArticleEntity

    @Transaction
    @Query("SELECT * FROM news")
    suspend fun getNewsWithArticle(): List<NewsWithArticles>

    @Query("DELETE FROM articles")
    suspend fun removeAllArticles()

    @Query("DELETE FROM news")
    suspend fun removeAllNews()

    @Transaction
    suspend fun removeNewsCache() {
        removeAllArticles()
        removeAllNews()
    }

}