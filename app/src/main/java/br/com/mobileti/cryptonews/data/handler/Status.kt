package br.com.mobileti.cryptonews.data.handler

sealed class Status {
    object Success : Status()
    class Error(val exception: Exception? = null) : Status()
    class Loading(val isLoading: Boolean) : Status()
}