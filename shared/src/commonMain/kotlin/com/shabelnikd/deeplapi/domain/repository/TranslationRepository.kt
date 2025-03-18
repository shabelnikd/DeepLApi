package com.shabelnikd.deeplapi.domain.repository

import com.shabelnikd.deeplapi.domain.models.TranslationRequest
import com.shabelnikd.deeplapi.domain.models.TranslationResult

interface TranslationRepository {
    suspend fun translateText(translationRequest: TranslationRequest): Result<TranslationResult>
}