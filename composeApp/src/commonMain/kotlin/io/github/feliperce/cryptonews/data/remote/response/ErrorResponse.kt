package io.github.feliperce.cryptonews.data.remote.response

data class ErrorResponse(
    val status: String = "",
    val code: String = "",
    val message: String = ""
)
