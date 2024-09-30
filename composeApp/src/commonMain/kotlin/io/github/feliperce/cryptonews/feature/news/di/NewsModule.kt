package io.github.feliperce.cryptonews.feature.news.di

import io.github.feliperce.cryptonews.feature.news.repository.NewsRepository
import io.github.feliperce.cryptonews.feature.news.viewmodel.NewsViewModel
import org.koin.dsl.module

val newsModule = module {
    single { NewsRepository(get()) }

    factory { NewsViewModel(get()) }
}