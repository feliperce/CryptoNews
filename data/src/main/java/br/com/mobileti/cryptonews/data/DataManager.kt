package br.com.mobileti.cryptonews.data

import br.com.mobileti.cryptonews.data.exception.ErrorException


sealed class Resource<T>(
    val data: T? = null,
    val isLoading: Boolean = false,
    val error: ErrorException? = null
) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Loading<T>(isLoading: Boolean) : Resource<T>(isLoading = isLoading)
    class Error<T>(error: ErrorException, data: T? = null) : Resource<T>(error = error, data = data)
}
