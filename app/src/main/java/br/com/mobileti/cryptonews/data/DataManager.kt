package br.com.mobileti.cryptonews.data

import br.com.mobileti.cryptonews.data.exception.ErrorException

data class Resource<out T>(
    val status: Status,
    val data: T? = null,
    val message: Int = -1
) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(
                status = Status.Success,
                data = data
            )
        }

        fun <T> error(exception: ErrorException? = null): Resource<T> {
            return Resource(
                status = Status.Error(exception)
            )
        }

        fun <T> loading(isLoading: Boolean): Resource<T> {
            return Resource(
                status = Status.Loading(isLoading)
            )
        }
    }
}

sealed class Status {
    object Success : Status()
    class Loading(val isLoading: Boolean) : Status()
    class Error(val exception: ErrorException? = null) : Status()
}