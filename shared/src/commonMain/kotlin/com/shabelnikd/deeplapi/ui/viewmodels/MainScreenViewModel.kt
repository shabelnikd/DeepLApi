package com.shabelnikd.deeplapi.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shabelnikd.deeplapi.domain.models.SupportedSourceLanguages
import com.shabelnikd.deeplapi.domain.models.SupportedTargetLanguages
import com.shabelnikd.deeplapi.domain.models.TranslationRequest
import com.shabelnikd.deeplapi.domain.models.TranslationResult
import com.shabelnikd.deeplapi.domain.usecases.TranslateTextUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class MainScreenViewModel(
    private val translateTextUseCase: TranslateTextUseCase
) : ViewModel(), KoinComponent {

    private val _editTextState = MutableStateFlow<RequestResult>(RequestResult.NotLoaded)
    val editTextState = _editTextState.asStateFlow()

    val currentRequestParams = MutableStateFlow(
        TranslationRequest(
            text = "",
            sourceLang = SupportedSourceLanguages.AUTO,
            targetLang = SupportedTargetLanguages.AUTO,
        )
    )

    fun updateCurrentRequestParams(
        text: String?,
        sourceLanguage: SupportedSourceLanguages?,
        targetLanguage: SupportedTargetLanguages?,
    ) = currentRequestParams.getAndUpdate { params ->
        params.copy(
            text = text ?: params.text,
            sourceLang = sourceLanguage ?: params.sourceLang,
            targetLang = targetLanguage ?: params.targetLang
        )
    }


    fun translateText() {
        viewModelScope.launch {
            currentRequestParams.value.text.takeIf { it.isNotEmpty() }?.apply {
                val result = translateTextUseCase(currentRequestParams.value)
                result.onSuccess { success ->

                    updateCurrentRequestParams(
                        sourceLanguage = success.detectedSourceLanguage?.let {
                            findSourceLanguageFromString(it)
                        } ?: SupportedSourceLanguages.AUTO,
                        text = null,
                        targetLanguage = null
                    )
                    _editTextState.value = RequestResult.Success(success)
                }.onFailure { err ->
                    _editTextState.value =
                        RequestResult.Error(err.message ?: "Ошибка соединения...")
                }
            } ?: run {
                updateCurrentRequestParams(
                    null, SupportedSourceLanguages.AUTO, null
                )
            }
        }
    }

    fun findSourceLanguageFromString(lang: String): SupportedSourceLanguages? {
        return SupportedSourceLanguages.entries.find { it.requestFieldName == lang }
    }

    sealed class RequestResult {
        data object NotLoaded : RequestResult()
        data class Success(val result: TranslationResult) : RequestResult()
        data class Error(val eMessage: String) : RequestResult()
    }


}