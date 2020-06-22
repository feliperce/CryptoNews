package br.com.mobileti.cryptonews.data.handler

import br.com.mobileti.cryptonews.data.exception.ErrorException

data class Resource<out T>(val status: Status, val data: T? = null, val message: Int = -1) {
    companion object {
        fun <T> remoteSuccess(data: T?): Resource<T> {
            return Resource(
                Status.Success,
                data,
                -1
            )
        }

        fun <T> localSuccess(data: T?): Resource<T> {
            return Resource(
                Status.Success,
                data,
                -1
            )
        }

        fun <T> remoteError(exception: ErrorException? = null): Resource<T> {
            return Resource(
                Status.Error(exception)
            )
        }

        fun <T> loading(isLoading: Boolean = false): Resource<T> {
            return Resource(
                Status.Loading(isLoading)
            )
        }
    }
}