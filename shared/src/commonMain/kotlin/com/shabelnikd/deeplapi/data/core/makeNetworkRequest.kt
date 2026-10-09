package com.shabelnikd.deeplapi.data.core

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess
import kotlinx.coroutines.TimeoutCancellationException

suspend inline fun <reified S> HttpClient.makeNetworkRequest(
    request: HttpRequestBuilder.() -> Unit,
    shouldUseAuth: Boolean = false,
    onSuccess: (S) -> Unit = {},
): Result<S> {
    return try {
        val httpResponse = request {
            request()
        }

        when {
            httpResponse.status.isSuccess() -> {
                onSuccess(httpResponse.body())
                Result.success(httpResponse.body())
            }

            !httpResponse.status.isSuccess() && httpResponse.status == HttpStatusCode.UnprocessableEntity -> {
                Result.failure(NetworkError.ApiInputs(httpResponse.body()))
            }

            else -> {
                Result.failure(
                    NetworkError.Api(httpResponse.body())
                )
            }
        }
    } catch (e: Exception) {
        when (e) {
            is ClientRequestException -> {
                when (e.response.status) {
                    HttpStatusCode.UnprocessableEntity -> {
                        Result.failure(NetworkError.ApiInputs(e.response.body()))
                    }

                    HttpStatusCode.InternalServerError -> {
                        Result.failure(NetworkError.Unexpected(e.response.body()))
                    }

                    HttpStatusCode.GatewayTimeout -> Result.failure(NetworkError.Timeout)
                    else -> Result.failure(
                        if (shouldUseAuth) NetworkError.AuthApi(e.response.body())
                        else NetworkError.Api(e.response.body())
                    )

                }
            }

            is ServerResponseException -> {
                if (e.response.status == HttpStatusCode.InternalServerError) {
                    Result.failure(NetworkError.Unexpected(e.response.body()))
                } else {
                    Result.failure(NetworkError.Unexpected("Unexpected server error: ${e.response.status.value}"))
                }
            }

            is SocketTimeoutException, is ConnectTimeoutException, is TimeoutCancellationException -> Result.failure(
                NetworkError.Timeout
            )

            else -> Result.failure(NetworkError.Unexpected(e.toString()))
        }
    }
}