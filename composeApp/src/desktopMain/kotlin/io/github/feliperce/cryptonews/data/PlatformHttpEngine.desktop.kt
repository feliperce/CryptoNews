package io.github.feliperce.cryptonews.data

import io.ktor.client.engine.*
import io.ktor.client.engine.okhttp.*

actual val httpEngine: HttpClientEngine
    get() = OkHttp.create()