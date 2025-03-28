package com.shabelnikd.deeplapi.data.repository

import com.shabelnikd.deeplapi.data.core.toDomain
import com.shabelnikd.deeplapi.data.core.toDto
import com.shabelnikd.deeplapi.data.datasource.deeplapi.TranslateApiService
import com.shabelnikd.deeplapi.domain.models.TranslationRequest
import com.shabelnikd.deeplapi.domain.models.TranslationResult
import com.shabelnikd.deeplapi.domain.repository.TranslationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

class TranslationRepositoryImpl(
    private val translateApiService: TranslateApiService,
) : TranslationRepository, KoinComponent {
    private val ioDispatcher: CoroutineDispatcher by inject(named("IODispatcher"))

    override suspend fun translateText(translationRequest: TranslationRequest): Flow<Result<TranslationResult>> =
        flow {
            val response = translateApiService.translateText(translationRequest.toDto())
            emit(response.map { it.toDomain() })
        }.flowOn(ioDispatcher)
}