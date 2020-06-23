package br.com.mobileti.cryptonews.extension

import br.com.mobileti.cryptonews.R
import br.com.mobileti.cryptonews.data.exception.ConnectionTimeoutException
import br.com.mobileti.cryptonews.data.exception.GenericException
import br.com.mobileti.cryptonews.data.exception.NoConnectionException
import br.com.mobileti.cryptonews.data.handler.Resource
import retrofit2.Response
import retrofit2.Retrofit
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

inline fun <reified T> Retrofit.networkCall(service: Retrofit.() -> Response<T>): Resource<T> {

    return try {

        val response = service()

        if (response.isSuccessful) {
            Resource.success(response.body())
        } else {
            Resource.error(GenericException(R.string.error_generic))
        }
    } catch (e: Exception) {

        when (e) {
            is UnknownHostException -> {
                Resource.error(NoConnectionException(R.string.no_connection))
            }
            is TimeoutException -> {
                Resource.error(ConnectionTimeoutException(R.string.error_timeout))
            }
            else -> Resource.error(GenericException(R.string.error_generic))
        }
    }
}