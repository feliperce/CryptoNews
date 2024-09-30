package io.github.feliperce.cryptonews.di

import io.github.feliperce.cryptonews.data.di.dataModule
import io.github.feliperce.cryptonews.feature.news.di.newsModule
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

fun initKoin(
    actualModules: List<Module> = listOf(),
    appDeclarations: KoinAppDeclaration = {}
) =
    startKoin {
        appDeclarations()
        modules(
            listOf(
                dataModule,
                newsModule
            ) + actualModules
        )
    }