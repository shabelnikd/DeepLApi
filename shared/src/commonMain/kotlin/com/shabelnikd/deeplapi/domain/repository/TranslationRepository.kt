package com.shabelnikd.deeplapi.domain.repository

import com.shabelnikd.deeplapi.domain.models.TranslationRequest
import com.shabelnikd.deeplapi.domain.models.TranslationResult
import kotlinx.coroutines.flow.Flow

interface TranslationRepository {
    suspend fun translateText(translationRequest: TranslationRequest): Flow<Result<TranslationResult>>
}