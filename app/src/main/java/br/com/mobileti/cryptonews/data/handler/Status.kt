package br.com.mobileti.cryptonews.data.handler

import br.com.mobileti.cryptonews.data.exception.ErrorException

sealed class Status {
    object Success : Status()
    class Error(val exception: ErrorException? = null) : Status()
    class Loading(val isLoading: Boolean) : Status()
    object CallingNetwork : Status()
    object WritingDb : Status()
}