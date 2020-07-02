package br.com.mobileti.cryptonews.feature.main.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import br.com.mobileti.cryptonews.R
import br.com.mobileti.cryptonews.feature.news.ui.NewsFragment
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named

class MainActivity : AppCompatActivity() {

    private val newsWorkerRequest: PeriodicWorkRequest by
    inject(named("DI_NEWS_WORKER_REQUEST"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, NewsFragment.newInstance())
                    .commitNow()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        WorkManager.getInstance(this).enqueue(newsWorkerRequest)
    }
}
