package br.com.mobileti.cryptonews

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import br.com.mobileti.cryptonews.data.local.db.NewsDb
import br.com.mobileti.cryptonews.data.local.entity.ArticleEntity
import br.com.mobileti.cryptonews.data.local.entity.NewsEntity
import br.com.mobileti.cryptonews.data.local.entity.SourceEntity
import br.com.mobileti.cryptonews.feature.home.repository.HomeRepository
import br.com.mobileti.cryptonews.ui.theme.CryptoNewsTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val repository: HomeRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CryptoNewsTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Column() {
                        /*val db = Room.databaseBuilder(
                            this@MainActivity,
                            NewsDb::class.java, "CryptoNews"
                        ).build()*/


                        Greeting("Android")
                        Button(onClick = {
                            GlobalScope.launch(Dispatchers.IO) {

                                repository.getCurrentNews().collect {
                                    Log.d("AAAAAA", "${it.data}")
                                }

                                /*db.newsDao().insertNewsWithArticles(
                                    newsEntity = NewsEntity(
                                        status = "status",
                                        totalResults = 1000
                                    ),
                                    articleEntityList = listOf(
                                        ArticleEntity(
                                            author = "author",
                                            content = "content",
                                            description = "desciprtion",
                                            publishedAt = "publishedat",
                                            title = "title",
                                            url = "url",
                                            urlToImage = "urltoimgg",
                                            source = SourceEntity(id = "dasdas", name = "das")
                                        )
                                    )
                                )*/
                            }

                        }) {
                            Text(text = "ADD")
                        }
                        Button(onClick = {

                        }) {
                            Text(text = "READ")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CryptoNewsTheme {
        Greeting("Android")
    }
}