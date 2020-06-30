package br.com.mobileti.cryptonews.data.local.dao


import androidx.room.*
import br.com.mobileti.cryptonews.data.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Transaction
    @Query("SELECT * FROM news")
    suspend fun getNews(): List<Article>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(articles: List<Article>)
    
    @Transaction
    @Query("DELETE FROM news")
    suspend fun deleteAllNews()

}