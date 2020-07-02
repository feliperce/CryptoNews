package br.com.mobileti.cryptonews.application

import android.app.Application
import br.com.mobileti.cryptonews.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DefaultApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(
                arrayListOf(
                    dbModule,
                    retrofitModule,
                    repositoryModule,
                    viewModelModule
                )
            )
        }
    }
}