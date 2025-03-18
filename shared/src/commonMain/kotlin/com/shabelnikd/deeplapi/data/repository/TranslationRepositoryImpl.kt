package com.shabelnikd.deeplapi.data.repository

import com.shabelnikd.deeplapi.data.datasource.deeplapi.TranslateApiService
import com.shabelnikd.deeplapi.data.core.toDomain
import com.shabelnikd.deeplapi.data.core.toDto
import com.shabelnikd.deeplapi.domain.models.TranslationRequest
import com.shabelnikd.deeplapi.domain.models.TranslationResult
import com.shabelnikd.deeplapi.domain.repository.TranslationRepository

class TranslationRepositoryImpl(
    private val translateApiService: TranslateApiService,
) : TranslationRepository  {
    override suspend fun translateText(translationRequest: TranslationRequest): Result<TranslationResult> {
        val response = translateApiService.translateText(translationRequest.toDto())
        return response.map { it.toDomain() }
    }
}