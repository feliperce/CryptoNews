package br.com.mobileti.cryptonews.home.di

import br.com.mobileti.cryptonews.data.local.db.NewsDb
import br.com.mobileti.cryptonews.data.remote.service.NewsService
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val homeDataModule = module {
    single {
        get<Retrofit>(
            named("retrofit")
        ).create(NewsService::class.java)
    }

    single {
        get<NewsDb>(
            named("db")
        ).newsDao()
    }
}