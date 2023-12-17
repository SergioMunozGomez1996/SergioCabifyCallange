package com.example.sergiocabifychallange.data.network

import com.example.sergiocabifychallange.utils.UiText

sealed class NetworkResponse<T>(
    val data: T? = null,
    val message: UiText? = null,
    val statusCode: Int? = null
) {
    class Success<T>(data: T?): NetworkResponse<T>(data)
    class Error<T>(code:Int, message: UiText.DynamicString, data: T? = null): NetworkResponse<T>(
        data,
        message,
        code
    )
    class Exception<T>(data: T? = null, message: UiText.StringResource) : NetworkResponse<T>(
        data,
        message,
        null
    )
}