package com.shabelnikd.deeplapi.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.shabelnikd.deeplapi.domain.models.SupportedSourceLanguages
import com.shabelnikd.deeplapi.ui.viewmodels.MainScreenViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun MainScreen() {
    val viewModel = koinViewModel<MainScreenViewModel>()
    val requestResult by viewModel.editTextState.collectAsState()
    var inputText by remember { mutableStateOf("") }
    val textFlow = remember { MutableStateFlow("") }
    val currentParams by viewModel.currentRequestParams.collectAsState()
//    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        InputTextField(
            inputText = inputText,
            label = { Text(text = currentParams.sourceLang.uiName) },
            onValueChange = {
                inputText = it
                textFlow.value = it
                viewModel.updateCurrentRequestParams(
                    text = it,
                    sourceLanguage = null,
                    targetLanguage = null
                )
            })

        DebouncedTranslation(textFlow = textFlow, onTranslate = { viewModel.translateText() })
        DebouncedDetectLanguage(
            textFlow = textFlow,
            onDetect = {
                viewModel.updateCurrentRequestParams(
                    null,
                    SupportedSourceLanguages.AUTO,
                    null
                )
            })

        TranslationResultDisplay(requestResult = requestResult)
    }

//    ErrorHandling(requestResult = requestResult, context = context, scope = scope, snackbarHostState = snackbarHostState)
}

@Composable
fun InputTextField(
    inputText: String,
    label: @Composable () -> Unit,
    onValueChange: (String) -> Unit
) {
    val brush = remember { Brush.linearGradient(colors = listOf(Color.Red, Color.Blue)) }
    TextField(
        value = inputText,
        onValueChange = { newValue ->
            onValueChange(newValue)
        },
        label = label,
        minLines = 10,
        textStyle = TextStyle(brush = brush),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
}

@OptIn(FlowPreview::class)
@Composable
fun DebouncedTranslation(textFlow: MutableStateFlow<String>, onTranslate: () -> Unit) {
    LaunchedEffect(textFlow) {
        textFlow
            .debounce(2000L)
            .onEach {
                onTranslate()
            }
            .launchIn(this)
    }
}

@OptIn(FlowPreview::class)
@Composable
fun DebouncedDetectLanguage(textFlow: MutableStateFlow<String>, onDetect: () -> Unit) {
    LaunchedEffect(textFlow) {
        textFlow.debounce(200L).onEach {
            onDetect()
        }
            .launchIn(this)

    }
}

@Composable
fun readOnlyTextField(label: @Composable () -> Unit, value: String) {
    val brush = remember { Brush.linearGradient(colors = listOf(Color.Red, Color.Blue)) }
    TextField(
        value = value,
        onValueChange = {},
        label = label,
        minLines = 10,
        textStyle = TextStyle(brush = brush),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        readOnly = true
    )
}

@Composable
fun TranslationResultDisplay(requestResult: MainScreenViewModel.RequestResult) {

    when (requestResult) {
        is MainScreenViewModel.RequestResult.Success -> {
            val result = requestResult.result
            result.text?.let {
                readOnlyTextField(
                    label = { result.detectedSourceLanguage?.let { lang -> Text(text = lang) } },
                    value = it
                )
            }

        }

        MainScreenViewModel.RequestResult.NotLoaded -> {
        }

        is MainScreenViewModel.RequestResult.Error -> {

        }
    }
}


//@Composable
//fun ErrorHandling(requestResult: MainScreenViewModel.RequestResult, scope: CoroutineScope, snackbarHostState: SnackbarHostState) {
//
//
//    if (requestResult is MainScreenViewModel.RequestResult.Error) {
//        LaunchedEffect(Unit) {
//            scope.launch {
//                sn
//            }
//        }
//    }
//}