package com.shabelnikd.deeplapi.android.ui.components.composable

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.shabelnikd.deeplapi.base.BaseViewModel
import com.shabelnikd.deeplapi.domain.models.TranslationRequest
import com.shabelnikd.deeplapi.domain.models.TranslationResult

@Composable
fun TranslationResultDisplay(
    requestResult: BaseViewModel.UiState<TranslationResult>,
    requestParams: TranslationRequest,
    currentTranslatedText: (text: String?) -> Unit
) {
    when (requestResult) {
        is BaseViewModel.UiState.Success -> {
            val result = requestResult.data
            currentTranslatedText.invoke(result.text)
            result.text?.let {
                ReadOnlyTextField(
                    label = { Text(text = requestParams.targetLang.uiName) },
                    value = it
                )
            }
        }

        is BaseViewModel.UiState.Loading -> {}

        is BaseViewModel.UiState.Error -> {}

        is BaseViewModel.UiState.NotLoaded -> {}
    }
}