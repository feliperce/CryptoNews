package br.com.mobileti.cryptonews.home.feature.home.di

import br.com.mobileti.cryptonews.home.feature.home.repository.HomeRepository
import br.com.mobileti.cryptonews.home.feature.home.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    factory { HomeRepository(get(), get()) }

    viewModel { HomeViewModel(get()) }
}