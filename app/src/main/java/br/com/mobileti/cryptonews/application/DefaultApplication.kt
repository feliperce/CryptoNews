package br.com.mobileti.cryptonews.application

import android.app.Application
import br.com.mobileti.cryptonews.data.di.dataModule
import br.com.mobileti.cryptonews.home.di.homeDataModule
import br.com.mobileti.cryptonews.home.feature.detail.di.detailModule
import br.com.mobileti.cryptonews.home.feature.home.di.homeModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

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
    }

}