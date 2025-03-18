package com.shabelnikd.deeplapi.data.core

import com.shabelnikd.deeplapi.data.models.TranslationRequestDto
import com.shabelnikd.deeplapi.data.models.TranslationResultDto
import com.shabelnikd.deeplapi.domain.models.TranslationRequest
import com.shabelnikd.deeplapi.domain.models.TranslationResult

fun TranslationRequest.toDto(): TranslationRequestDto {
    return TranslationRequestDto(
        text = this.text,
        sourceLang = this.sourceLang.requestFieldName,
        targetLang = this.targetLang.requestFieldName,
    )
}

fun TranslationResultDto.toDomain(): TranslationResult {
    return TranslationResult(
        detectedSourceLanguage = this.translations?.first()?.detectedSourceLanguage,
        text = this.translations?.first()?.text
    )
}