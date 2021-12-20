package br.com.mobileti.cryptonews.data.remote

import android.content.Context
import android.widget.Toast
import br.com.mobileti.cryptonews.R
import kotlinx.coroutines.*
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class KeyInterceptor(
    private val context: Context
) : Interceptor {

    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)

    override fun intercept(chain: Interceptor.Chain): Response {

        val request: Request = chain.request()
        val response = chain.proceed(request)
        if (response.code == 401) {
            scope.launch {
                Toast.makeText(context, R.string.error_auth, Toast.LENGTH_LONG).show()
            }
        }
        return response
    }

}