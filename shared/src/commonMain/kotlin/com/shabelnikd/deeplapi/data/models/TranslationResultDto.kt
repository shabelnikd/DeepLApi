package com.shabelnikd.deeplapi.data.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TranslationResultDto(
    @SerialName("translations")
    val translations: List<Translation?>?
) {
    @Serializable
    data class Translation(
        @SerialName("detected_source_language")
        val detectedSourceLanguage: String?,
        @SerialName("text")
        val text: String?
    )
}