package br.com.mobileti.cryptonews.di

import android.content.Context
import androidx.room.Room
import androidx.work.*
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
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val workerModule = module {
    factory { WorkManager.getInstance(androidContext()) }
}

val dbModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            Database::class.java, "CryptoNews"
        ).build()
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

    fun newsApiService(retrofit: Retrofit): NewsService {
        return retrofit.create(NewsService::class.java)
    }

    single { retrofit() }
    single { newsApiService(get()) }
}

val repositoryModule = module {
    factory { NewsRepository(get(), get()) }
}

val viewModelModule = module {
    viewModel { NewsViewModel(get(), get()) }
}