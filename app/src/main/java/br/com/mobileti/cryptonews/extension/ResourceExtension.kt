package br.com.mobileti.cryptonews.extension

import br.com.mobileti.cryptonews.R
import br.com.mobileti.cryptonews.data.exception.ConnectionTimeoutException
import br.com.mobileti.cryptonews.data.exception.GenericException
import br.com.mobileti.cryptonews.data.exception.NoConnectionException
import br.com.mobileti.cryptonews.data.handler.Resource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import retrofit2.Response
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

inline fun <reified REMOTE, LOCAL> callResourceData(
    crossinline local: suspend () -> LOCAL,
    crossinline remote: suspend () -> Response<REMOTE>,
    crossinline shouldFetchFromRemote: (LOCAL?) -> Boolean = { true },
    crossinline onRemoteResource: (REMOTE?) -> Resource<LOCAL>,
    crossinline onFinish: () -> Unit = { }
) = flow {

    val localData: LOCAL? = local()
    val fromRemote = shouldFetchFromRemote(localData)

    if (fromRemote) {
        if (remote().isSuccessful) {
            val remoteData = remote().body()
            emit(onRemoteResource(remoteData))
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
            emit(Resource.error(GenericException(R.string.error_generic)))
        }
    }
}.onCompletion {
    onFinish()
}