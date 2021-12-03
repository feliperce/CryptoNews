package br.com.mobileti.cryptonews.extension

import br.com.mobileti.cryptonews.R
import br.com.mobileti.cryptonews.data.Resource
import br.com.mobileti.cryptonews.data.exception.ConnectionTimeoutException
import br.com.mobileti.cryptonews.data.exception.ErrorException
import br.com.mobileti.cryptonews.data.exception.GenericException
import br.com.mobileti.cryptonews.data.exception.NoConnectionException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

suspend fun <MAPPER, LOCAL, REMOTE> FlowCollector<Resource<MAPPER>>.syncData(
    local: () -> LOCAL,
    remote: () -> Response<REMOTE>,
    onRemote: (REMOTE?) -> LOCAL,
    mapper: (LOCAL?) -> MAPPER,
    shouldFetchFromRemote: (LOCAL?) -> Boolean,
    onFinish: () -> Unit = { },
    onException: (error: Throwable) -> Unit? = { }
) {
    runCatching {
        var localData: LOCAL?

        withContext(Dispatchers.IO) {
            localData = local()
        }

        val fromRemote = shouldFetchFromRemote(localData)

        if (fromRemote) {
            if (remote().isSuccessful) {
                val remoteData = remote().body()
                val newLocalData: LOCAL

                withContext(Dispatchers.IO) {
                    newLocalData = onRemote(remoteData)
                }

                emit(Resource.success(mapper(newLocalData)))
            } else {
                emit(Resource.error(GenericException(R.string.error_generic)))
            }
        } else {
            emit(Resource.success(mapper(localData)))
        }
    }.onFailure {
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
    }.onSuccess {
        onFinish()
    }
}

