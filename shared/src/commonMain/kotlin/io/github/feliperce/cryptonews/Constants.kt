package io.github.feliperce.cryptonews

import CryptoNews.shared.BuildConfig

const val SERVER_PORT = 8080
const val SERVER_HOST = BuildConfig.SV_HOST
const val SERVER_BASE_URL = "http://${SERVER_HOST}:${SERVER_PORT}"