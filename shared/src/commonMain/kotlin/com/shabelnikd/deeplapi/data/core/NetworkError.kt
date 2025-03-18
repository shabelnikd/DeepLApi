package com.shabelnikd.deeplapi.data.core


sealed class NetworkError(message: String? = null) : Throwable(message) {

    class Unexpected(message: String?) : NetworkError(message)

    class Api(message: String?) : NetworkError(message)

    class AuthApi(message: String?) : NetworkError(message)

    class ApiInputs(message: String?) : NetworkError(message)

    object Timeout : NetworkError("Timeout")
}