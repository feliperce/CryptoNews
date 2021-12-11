package br.com.mobileti.cryptonews.feature.home.di

import br.com.mobileti.cryptonews.feature.home.repository.HomeRepository
import org.koin.dsl.module

val homeModule = module {
    factory { HomeRepository(get(), get()) }
}