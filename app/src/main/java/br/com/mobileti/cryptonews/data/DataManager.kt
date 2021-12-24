package br.com.mobileti.cryptonews.data



sealed class Resource<T>(
    val data: T? = null,
    val isLoading: Boolean = false,
    val error: Exception? = null
) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Loading<T>(isLoading: Boolean) : Resource<T>(isLoading = isLoading)
    class Error<T>(error: Exception, data: T? = null) : Resource<T>(error = error, data = data)
}
