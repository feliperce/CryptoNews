package br.com.mobileti.cryptonews.data.di

import androidx.room.Room
import br.com.mobileti.cryptonews.data.BuildConfig
import br.com.mobileti.cryptonews.data.local.db.NewsDb
import br.com.mobileti.cryptonews.data.remote.KeyInterceptor
import br.com.mobileti.cryptonews.data.remote.service.NewsService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    single(named("db")) {
        Room.databaseBuilder(
            androidContext(),
            NewsDb::class.java, "CryptoNews"
        ).build()
    }

    fun retrofit(keyInterceptor: KeyInterceptor): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level =
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else HttpLoggingInterceptor.Level.NONE

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(keyInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.ENDPOINT_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single(named("retrofit")) {
        retrofit(get())
    }

    factory { KeyInterceptor(androidContext()) }
}
