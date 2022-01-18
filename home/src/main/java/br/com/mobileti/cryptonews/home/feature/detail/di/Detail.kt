package br.com.mobileti.cryptonews.home.feature.detail.di

import br.com.mobileti.cryptonews.home.feature.detail.repository.DetailRepository
import br.com.mobileti.cryptonews.feature.detail.viewmodel.DetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val detailModule = module {

    factory { DetailRepository(get()) }

    viewModel { DetailViewModel(get()) }
}