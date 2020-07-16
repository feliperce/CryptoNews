package br.com.mobileti.cryptonews.di

import androidx.room.Room
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import br.com.mobileti.cryptonews.BuildConfig
import br.com.mobileti.cryptonews.data.local.db.Database
import br.com.mobileti.cryptonews.data.remote.service.NewsService
import br.com.mobileti.cryptonews.data.worker.NewsWorker
import br.com.mobileti.cryptonews.feature.news.repository.NewsRepository
import br.com.mobileti.cryptonews.feature.news.viewmodel.NewsViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val workerModule = module {
    single(named("DI_NEWS_WORKER_REQUEST")) {
        val constraints: Constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        PeriodicWorkRequestBuilder<NewsWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .setInitialDelay(5, TimeUnit.MINUTES)
            .addTag(NewsWorker.TAG_WORKER)
            .build()
    }
}

val dbModule = module {
    single {
        val db = Room.databaseBuilder(
            androidContext(),
            Database::class.java, "CryptoNews"
        ).build()

        db.newsDao()
    }

}

val retrofitModule = module {

    fun retrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level =
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else HttpLoggingInterceptor.Level.NONE

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.ENDPOINT_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun newsApiService(): NewsService {
        return retrofit().create(NewsService::class.java)
    }

    single { newsApiService() }
}

val repositoryModule = module {
    factory { NewsRepository(get(), get()) }
}

val viewModelModule = module {
    viewModel { NewsViewModel(get()) }
}