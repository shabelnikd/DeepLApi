package com.shabelnikd.deeplapi.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TranslationRequestDto(
    @SerialName("text")
    val text: String,
    @SerialName("source_lang")
    val sourceLang: String,
    @SerialName("target_lang")
    val targetLang: String,
)

