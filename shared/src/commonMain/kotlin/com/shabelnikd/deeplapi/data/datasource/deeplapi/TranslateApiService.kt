package com.shabelnikd.deeplapi.data.datasource.deeplapi

import com.shabelnikd.deeplapi.data.core.makeNetworkRequest
import com.shabelnikd.deeplapi.data.models.TranslationRequestDto
import com.shabelnikd.deeplapi.data.models.TranslationResultDto
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.header
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters
import io.ktor.http.contentType

class TranslateApiService(
    private val httpClient: HttpClient
) {
    suspend fun translateText(translationRequestDto: TranslationRequestDto): Result<TranslationResultDto> =
        httpClient.makeNetworkRequest({
            url("https://api-free.deepl.com/v2/translate")
            method = HttpMethod.Post
            contentType(ContentType.Application.FormUrlEncoded)

            setBody(
                body = FormDataContent(
                    Parameters.build {
                        header(
                            "Authorization",
                            "DeepL-Auth-Key c7cab57a-64dc-457b-b980-38223a2eb5b5:fx"
                        )
                        append("text", translationRequestDto.text)
                        append("source_lang", translationRequestDto.sourceLang)
                        append("target_lang", translationRequestDto.targetLang)
                    },
                )
            )

        })
}