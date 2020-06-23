package br.com.mobileti.cryptonews.application

import android.app.Application
import br.com.mobileti.cryptonews.di.repositoryModule
import br.com.mobileti.cryptonews.di.retrofitModule
import br.com.mobileti.cryptonews.di.viewModelModule
import org.koin.core.context.startKoin

class DefaultApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(
                arrayListOf(
                    retrofitModule,
                    repositoryModule,
                    viewModelModule
                )
            )
        }
    }
}