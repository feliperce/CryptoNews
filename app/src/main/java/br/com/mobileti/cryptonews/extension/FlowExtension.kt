package br.com.mobileti.cryptonews.extension

import br.com.mobileti.cryptonews.R
import br.com.mobileti.cryptonews.data.exception.ConnectionTimeoutException
import br.com.mobileti.cryptonews.data.exception.GenericException
import br.com.mobileti.cryptonews.data.exception.NoConnectionException
import br.com.mobileti.cryptonews.data.handler.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

fun <T> Flow<Resource<T>>.catchResource(action: suspend FlowCollector<Resource<T>>.(cause: Throwable) -> Resource<T>): Flow<Resource<T>> =
    catch { throwable ->
        val resource: Resource<T> = when (throwable) {
            is UnknownHostException -> {
                Resource.error(NoConnectionException(R.string.no_connection))
            }
            is TimeoutException -> {
                Resource.error(ConnectionTimeoutException(R.string.error_timeout))
            }
            else -> {
                action.invoke(this, throwable)
            }
        }

        emit(resource)
    }


/*
when (throwable) {
    is UnknownHostException -> {
        Resource.error(NoConnectionException(R.string.no_connection))
    }
    is TimeoutException -> {
        Resource.error(ConnectionTimeoutException(R.string.error_timeout))
    }
    else -> Resource.error(GenericException(R.string.error_generic))
}*/
