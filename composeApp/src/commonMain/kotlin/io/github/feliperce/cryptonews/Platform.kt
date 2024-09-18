package io.github.feliperce.cryptonews

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform