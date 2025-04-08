package com.shabelnikd.deeplapi.domain.models

data class TranslationRequest(
    val text: String,
    val sourceLang: SupportedSourceLanguages,
    val targetLang: SupportedTargetLanguages,
    var isSourceHandSelected: Boolean = false,
)

