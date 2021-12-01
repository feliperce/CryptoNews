package br.com.mobileti.cryptonews.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.mobileti.cryptonews.data.local.dao.NewsDao
import br.com.mobileti.cryptonews.data.local.entity.ArticleEntity
import br.com.mobileti.cryptonews.data.local.entity.NewsEntity

@Database(entities = [
    NewsEntity::class,
    ArticleEntity::class
],
    version = 1
)
abstract class NewsDb : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}