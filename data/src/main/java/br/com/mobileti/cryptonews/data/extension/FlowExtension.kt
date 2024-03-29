package br.com.mobileti.cryptonews.data.extension

import br.com.mobileti.cryptonews.data.R
import br.com.mobileti.cryptonews.data.Resource
import br.com.mobileti.cryptonews.data.exception.ConnectionTimeoutException
import br.com.mobileti.cryptonews.data.exception.GenericException
import br.com.mobileti.cryptonews.data.exception.NoConnectionException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException


inline fun <reified REMOTE, LOCAL, MAPPER> syncData(
    crossinline local: suspend () -> LOCAL,
    crossinline remote: suspend () -> Response<REMOTE>,
    crossinline mapper: suspend (LOCAL?) -> MAPPER,
    crossinline onRemote: suspend (REMOTE) -> Unit,
    crossinline shouldFetchFromRemote: suspend (data: LOCAL?) -> Boolean,
    crossinline onFinish: () -> Unit = { },
    crossinline onException: FlowCollector<Resource<MAPPER>>.(error: Throwable) -> Unit? = { }
) = flow<Resource<MAPPER>> {

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

                emit(Resource.Success(mapper(newLocalData)))
            } else {
                emit(Resource.Error(GenericException(R.string.error_generic)))
            }
        } else {
            emit(Resource.Error(GenericException(R.string.error_generic)))
        }
    } else {
        emit(Resource.Success(mapper(localData)))
    }

}.catch {
    when (it) {
        is UnknownHostException -> {
            emit(Resource.Error(NoConnectionException(R.string.no_connection)))
        }
        is TimeoutException -> {
            emit(Resource.Error(ConnectionTimeoutException(R.string.error_timeout)))
        }
        else -> {
            if (onException.invoke(this, it) == null) {
                emit(Resource.Error(GenericException(R.string.error_generic)))
            }
        }
    }
}.onCompletion {
    emit(Resource.Loading(false))
    onFinish()
}.onStart {
    emit(Resource.Loading(true))
}

fun <LOCAL, MAPPER> callLocalData(
    local: suspend () -> LOCAL,
    mapper: suspend (LOCAL?) -> MAPPER,
    onFinish: suspend () -> Unit = { },
    onException: FlowCollector<Resource<MAPPER>>.(error: Throwable) -> Unit? = { }
) = flow<Resource<MAPPER>> {
    val localData = local()
    emit(Resource.Success(mapper(localData)))
}.catch {
    onException(this, it)
}.onStart {
    emit(Resource.Loading(true))
}.onCompletion {
    emit(Resource.Loading(false))
    onFinish()
}.flowOn(Dispatchers.IO)


