package io.github.feliperce.cryptonews.data.remote.mapper

import io.github.feliperce.cryptonews.data.remote.response.ErrorResponse
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class ErrorData(
    val id: String = Uuid.random().toHexString(),
    val status: String = "",
    val code: String = "",
    val message: String = ""
)

fun ErrorResponse.toErrorData() =
    ErrorData(
        status = status,
        code = code,
        message = message
    )