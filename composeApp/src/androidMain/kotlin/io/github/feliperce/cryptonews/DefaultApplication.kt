package io.github.feliperce.cryptonews

import android.app.Application
import io.github.feliperce.cryptonews.di.initKoin

class DefaultApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {  }
    }
}