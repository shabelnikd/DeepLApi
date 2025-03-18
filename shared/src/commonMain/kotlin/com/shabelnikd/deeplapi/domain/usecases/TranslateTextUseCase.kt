package com.shabelnikd.deeplapi.domain.usecases

import com.shabelnikd.deeplapi.domain.models.TranslationRequest
import com.shabelnikd.deeplapi.domain.repository.TranslationRepository

class TranslateTextUseCase(private val translationRepository: TranslationRepository) {
    suspend operator fun invoke(translationRequest: TranslationRequest) =
        translationRepository.translateText(translationRequest)
}