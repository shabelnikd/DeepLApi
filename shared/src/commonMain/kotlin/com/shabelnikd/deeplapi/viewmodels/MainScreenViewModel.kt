package com.shabelnikd.deeplapi.viewmodels

import com.shabelnikd.deeplapi.base.BaseViewModel
import com.shabelnikd.deeplapi.domain.models.SupportedSourceLanguages
import com.shabelnikd.deeplapi.domain.models.SupportedTargetLanguages
import com.shabelnikd.deeplapi.domain.models.TranslationRequest
import com.shabelnikd.deeplapi.domain.models.TranslationResult
import com.shabelnikd.deeplapi.domain.usecases.TranslateTextUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import org.koin.core.component.KoinComponent


class MainScreenViewModel(
    private val translateTextUseCase: TranslateTextUseCase
) : BaseViewModel(), KoinComponent {

    private val _editTextState = MutableStateFlow<UiState<TranslationResult>>(UiState.NotLoaded)
    val editTextState = _editTextState.asStateFlow()

    var isLangHandSelected = false


    val currentRequestParams = MutableStateFlow(
        TranslationRequest(
            text = "",
            sourceLang = SupportedSourceLanguages.AUTO,
            targetLang = SupportedTargetLanguages.RU,
        )
    )

    fun updateCurrentRequestParams(
        text: String? = null,
        sourceLanguage: SupportedSourceLanguages? = null,
        targetLanguage: SupportedTargetLanguages? = null,
    ) = currentRequestParams.getAndUpdate { params ->
        params.copy(
            text = text ?: params.text,
            sourceLang = sourceLanguage ?: params.sourceLang,
            targetLang = targetLanguage ?: params.targetLang,
        )
    }


    fun translateText() {
        currentRequestParams.value.text.takeIf { it.isNotEmpty() }?.let {
            collectFlow(
                stateFlow = _editTextState,
                request = { translateTextUseCase(currentRequestParams.value) },
                onSuccess = { success ->
                    updateCurrentRequestParams(
                        sourceLanguage =
                            if (isLangHandSelected) null
                            else success.detectedSourceLanguage?.let {
                                findSourceLanguageFromString(it)
                            } ?: SupportedSourceLanguages.AUTO,
                    )
                },
                onError = { err ->
                    println("Translation error: ${err.message}")
                }
            )
        } ?: run {
            _editTextState.value = UiState.NotLoaded
        }
    }

    private fun findSourceLanguageFromString(lang: String): SupportedSourceLanguages? {
        return SupportedSourceLanguages.entries.find { it.requestFieldName == lang }
    }

}