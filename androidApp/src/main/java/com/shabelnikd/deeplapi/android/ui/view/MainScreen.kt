package com.shabelnikd.deeplapi.android.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.shabelnikd.deeplapi.android.ui.base.BaseScreen
import com.shabelnikd.deeplapi.android.ui.base.LocalViewModel
import com.shabelnikd.deeplapi.android.ui.components.composable.InputTextField
import com.shabelnikd.deeplapi.android.ui.components.composable.TranslationResultDisplay
import com.shabelnikd.deeplapi.android.ui.components.effects.DebouncedDetectLanguage
import com.shabelnikd.deeplapi.android.ui.components.effects.DebouncedTranslation
import com.shabelnikd.deeplapi.android.viewmodels.CartoonViewModel
import com.shabelnikd.deeplapi.domain.models.SupportedSourceLanguages
import com.shabelnikd.deeplapi.domain.models.SupportedTargetLanguages
import com.shabelnikd.deeplapi.viewmodels.MainScreenViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.compose.viewmodel.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val viewModel = koinViewModel<MainScreenViewModel>()
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()


    BaseScreen(viewModel = viewModel, snackBarHostState = snackBarHostState) {
        val vm = LocalViewModel.current as? MainScreenViewModel ?: error("ViewModel not provided")

        val currentParams by vm.currentRequestParams.collectAsStateWithLifecycle()
        val requestResult by vm.editTextState.collectAsStateWithLifecycle()
        var currentTranslatedText by remember { mutableStateOf("") }
        var inputText by remember { mutableStateOf("") }
        val textFlow = remember { MutableStateFlow("") }

        val buttonColors = ButtonColors(
            Color.White,
            contentColor = Color.Black,
            disabledContentColor = Color.Gray,
            disabledContainerColor = Color.Black
        )

        // Bottom Sheet

        var showSourceSheet by remember { mutableStateOf(false) }
        var showTargetSheet by remember { mutableStateOf(false) }



        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            DebouncedTranslation(textFlow = textFlow, onTranslate = { vm.translateText() })

            DebouncedDetectLanguage(
                textFlow = textFlow,
                onDetect = {
                    if (!vm.isLangHandSelected) {
                        viewModel.updateCurrentRequestParams(
                            sourceLanguage = SupportedSourceLanguages.AUTO,
                        )
                    }
                })


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {

                TextButton(
                    onClick = {
                        showSourceSheet = true
                    },
                    content = { Text(currentParams.sourceLang.uiName) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(0.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = buttonColors
                )

                TextButton(
                    onClick = {
                        vm.updateCurrentRequestParams(
                            sourceLanguage = SupportedSourceLanguages.entries.find { it.requestFieldName == currentParams.targetLang.requestFieldName },
                            targetLanguage = SupportedTargetLanguages.entries.find { it.requestFieldName == currentParams.sourceLang.requestFieldName },
                        )
                        inputText = currentTranslatedText
                        vm.isLangHandSelected = true
                        vm.translateText()
                    },
                    content = { Text("<->") },
                    modifier = Modifier.weight(0.5f),
                    shape = RoundedCornerShape(0.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = buttonColors
                )

                TextButton(
                    onClick = {
                        showTargetSheet = true
                    },
                    content = { Text(currentParams.targetLang.uiName) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(0.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = buttonColors
                )
            }


            InputTextField(
                inputText = inputText,
                label = { Text(text = currentParams.sourceLang.uiName) },
                onValueChange = {
                    inputText = it
                    textFlow.value = it
                    vm.updateCurrentRequestParams(
                        text = it,
                        sourceLanguage = null,
                        targetLanguage = null
                    )
                })

            TranslationResultDisplay(
                requestResult = requestResult,
                requestParams = currentParams,
                currentTranslatedText = { currentTranslatedText = it.orEmpty() })


            val cartoonViewModel: CartoonViewModel = koinViewModel<CartoonViewModel>()
            cartoonViewModel.getCharacters()
            val characters = cartoonViewModel.charactersState.collectAsLazyPagingItems()

            LazyColumn {
                items(characters.itemCount) { index ->
                    val character = characters[index]
                    if (character != null) {
                        Text(text = character.name.orEmpty())
                        Text(text = character.id.toString())
                    }
                }
            }


            if (showSourceSheet) {
                ShowLangSelect(
                    items = SupportedSourceLanguages.entries.toList(),
                    onClick = { lang ->
                        vm.updateCurrentRequestParams(
                            sourceLanguage = lang,
                        )
                        vm.isLangHandSelected = true
                        vm.translateText()
                    },
                    content = { lang ->
                        Text(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 10.dp),
                            text = lang.uiName,
                            textAlign = TextAlign.Start,
                        )
                    },
                    onDismiss = { showSourceSheet = false }
                )
            }


            if (showTargetSheet) {
                ShowLangSelect(
                    items = SupportedTargetLanguages.entries.toList(),
                    onClick = { lang ->
                        vm.updateCurrentRequestParams(
                            targetLanguage = lang,
                        )
                        vm.translateText()
                    },
                    content = { lang ->
                        Text(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 10.dp),
                            text = lang.uiName,
                            textAlign = TextAlign.Start,
                        )
                    },
                    onDismiss = { showTargetSheet = false }
                )
            }

        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> ShowLangSelect(
    items: List<T>,
    onClick: (lang: T) -> Unit,
    content: @Composable (uiLang: T) -> Unit,
    onDismiss: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()

    val buttonColors = ButtonColors(
        Color.White,
        contentColor = Color.Black,
        disabledContentColor = Color.Gray,
        disabledContainerColor = Color.Black
    )

    ModalBottomSheet(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),

        onDismissRequest = onDismiss,
        sheetState = sheetState,
    ) {
        LazyColumn {
            items(items) { item ->
                TextButton(
                    onClick = {
                        onClick.invoke(item)
                    },
                    content = {
                        content.invoke(item)
                    },
                    modifier = Modifier
                        .fillMaxSize(),
                    shape = RoundedCornerShape(0.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = buttonColors
                )
            }
        }
    }
}

