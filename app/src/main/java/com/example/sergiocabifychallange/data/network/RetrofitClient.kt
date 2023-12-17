package com.example.sergiocabifychallange.data.network

import com.example.sergiocabifychallange.R
import com.example.sergiocabifychallange.domain.model.Product
import com.example.sergiocabifychallange.domain.model.toDomainLayerType
import com.example.sergiocabifychallange.utils.UiText
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.inject.Inject

class RetrofitClient @Inject constructor(
    private val api: CabifyApiService
){
    companion object {
        private const val ERROR_MESSAGE = "undefined error"
    }

    suspend fun getProducts(): NetworkResponse<List<Product>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getProducts()
                if (response.isSuccessful && response.body() != null) {
                    NetworkResponse.Success(response.body()?.products?.map { it.toDomainLayerType() })
                } else {

                    NetworkResponse.Error(
                        message = UiText.DynamicString(
                            ERROR_MESSAGE
                        ),
                        data = null,
                        code = response.code()
                    )
                }
            } catch (e: Exception) {
                getExceptionResponse(e)
            }

        }
    }

    private fun <T> getExceptionResponse(t: Throwable): NetworkResponse<T> {
        when (t) {
            is ConnectException -> {
                return NetworkResponse.Exception(
                    message = UiText.StringResource(R.string.network_access_error)
                )
            }
            is HttpException -> {
                return NetworkResponse.Exception(
                    message = UiText.StringResource(R.string.server_error)
                )
            }
            is SocketTimeoutException -> {
                return NetworkResponse.Exception(
                    message = UiText.StringResource(R.string.server_error)
                )
            }

            is IOException -> {
                return NetworkResponse.Exception(
                    message = UiText.StringResource(R.string.network_access_error)
                )
            }

            is CancellationException -> {
                return NetworkResponse.Exception(
                    message = UiText.StringResource(R.string.cancel)
                )
            }

            else -> {
                return NetworkResponse.Exception(
                    message = UiText.StringResource(R.string.server_error)
                )
            }
        }
    }
}