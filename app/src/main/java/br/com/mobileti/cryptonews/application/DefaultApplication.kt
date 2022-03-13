package br.com.mobileti.cryptonews.application

import android.app.Application
import androidx.work.*
import br.com.mobileti.cryptonews.data.di.dataModule
import br.com.mobileti.cryptonews.home.di.homeDataModule
import br.com.mobileti.cryptonews.home.feature.detail.di.detailModule
import br.com.mobileti.cryptonews.home.feature.home.di.homeModule
import br.com.mobileti.cryptonews.home.feature.home.worker.NewsUpdateWorker
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.util.concurrent.TimeUnit

class DefaultApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(
                arrayListOf(
                    dataModule,
                    homeModule,
                    detailModule,
                    homeDataModule
                )
            )
        }

        WorkManager
            .getInstance(applicationContext)
            .enqueue(setupNewsUpdateWorker())
    }

    private fun setupNewsUpdateWorker(): WorkRequest {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        return PeriodicWorkRequestBuilder<NewsUpdateWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .setInitialDelay(5, TimeUnit.MINUTES)
            .addTag(NewsUpdateWorker.TAG)
            .build()
    }


}