package io.github.feliperce.cryptonews.data

import io.ktor.client.engine.*
import io.ktor.client.engine.darwin.*

actual val httpEngine: HttpClientEngine
    get() = Darwin.create {  }