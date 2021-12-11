package br.com.mobileti.cryptonews.extension

import br.com.mobileti.cryptonews.R
import br.com.mobileti.cryptonews.data.Resource
import br.com.mobileti.cryptonews.data.Status
import br.com.mobileti.cryptonews.data.exception.ConnectionTimeoutException
import br.com.mobileti.cryptonews.data.exception.ErrorException
import br.com.mobileti.cryptonews.data.exception.GenericException
import br.com.mobileti.cryptonews.data.exception.NoConnectionException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException


inline fun <reified REMOTE, LOCAL> syncData(
    crossinline local: suspend () -> LOCAL,
    crossinline remote: suspend () -> Response<REMOTE>,
    crossinline onRemote: suspend (REMOTE) -> Unit,
    crossinline shouldFetchFromRemote: (data: LOCAL?) -> Boolean,
    crossinline onFinish: () -> Unit = { },
    crossinline onException: (error: Throwable) -> Unit? = { }
) = flow<Resource<LOCAL>> {

    val localData: LOCAL? = local()

    val fromRemote = shouldFetchFromRemote(localData)

    if (fromRemote) {
        if (remote().isSuccessful) {
            val remoteData = remote().body()
            val newLocalData: LOCAL
            if (remoteData != null) {

                newLocalData = withContext(Dispatchers.IO) {
                    onRemote(remoteData)
                    local()
                }

                emit(Resource.success(newLocalData))
            } else {
                emit(Resource.error(GenericException(R.string.error_generic)))
            }
        } else {
            emit(Resource.error(GenericException(R.string.error_generic)))
        }
    } else {
        emit(Resource.success(localData))
    }

}.catch {
    when (it) {
        is UnknownHostException -> {
            emit(Resource.error(NoConnectionException(R.string.no_connection)))
        }
        is TimeoutException -> {
            emit(Resource.error(ConnectionTimeoutException(R.string.error_timeout)))
        }
        else -> {
            if (onException.invoke(it) == null) {
                emit(Resource.error(GenericException(R.string.error_generic)))
            }
        }
    }
}.onCompletion {
    emit(Resource.loading(false))
    onFinish()
}
