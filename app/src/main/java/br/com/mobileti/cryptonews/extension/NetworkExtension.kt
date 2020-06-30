package br.com.mobileti.cryptonews.extension

import br.com.mobileti.cryptonews.R
import br.com.mobileti.cryptonews.data.exception.ConnectionTimeoutException
import br.com.mobileti.cryptonews.data.exception.GenericException
import br.com.mobileti.cryptonews.data.exception.NoConnectionException
import br.com.mobileti.cryptonews.data.handler.Resource
import retrofit2.Response
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

inline fun <reified T> Response<T>.networkCall(block: Response<T>.(resource: Resource<T>) -> Unit) {

    var resource: Resource<T>? = null

    try {

        resource = if (isSuccessful) {
            Resource.success(body())
        } else {
            Resource.error(GenericException(R.string.error_generic))
        }
    } catch (e: Exception) {

        resource = when (e) {
            is UnknownHostException -> {
                Resource.error(NoConnectionException(R.string.no_connection))
            }
            is TimeoutException -> {
                Resource.error(ConnectionTimeoutException(R.string.error_timeout))
            }
            else -> Resource.error(GenericException(R.string.error_generic))
        }
    }
    finally {
        resource?.let { block.invoke(this, it) }
    }
}