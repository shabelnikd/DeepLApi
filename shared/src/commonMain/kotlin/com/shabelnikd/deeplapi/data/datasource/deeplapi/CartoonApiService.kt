package com.shabelnikd.deeplapi.data.datasource.deeplapi

import com.shabelnikd.deeplapi.data.core.makeNetworkRequest
import com.shabelnikd.deeplapi.data.models.page.BaseResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.http.HttpMethod

class CartoonApiService(
    private val httpClient: HttpClient,
) {
    private val baseUrl: String = "https://rickandmortyapi.com/api"

    suspend fun getCharacters(page: Int): Result<BaseResponseDto> =
        httpClient.makeNetworkRequest({
            url("$baseUrl/character")
            method = HttpMethod.Get
            parameter("page", page)
        })
}