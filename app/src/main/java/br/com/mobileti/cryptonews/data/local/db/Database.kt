package br.com.mobileti.cryptonews.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.mobileti.cryptonews.data.local.dao.NewsDao
import br.com.mobileti.cryptonews.data.model.Article

@Database(entities = [
    Article::class
],
    version = 1
)
abstract class Database : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}