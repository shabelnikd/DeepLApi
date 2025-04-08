package com.shabelnikd.deeplapi.android.viewmodels

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.shabelnikd.deeplapi.android.data.repository.CartoonRepository
import com.shabelnikd.deeplapi.base.BaseViewModel
import com.shabelnikd.deeplapi.domain.models.page.Character
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent


class CartoonViewModel(
    private val cartoonRepository: CartoonRepository
) : BaseViewModel(), KoinComponent {


    private val _charactersState =
        MutableStateFlow<PagingData<Character>>(PagingData.empty())
    val charactersState = _charactersState.asStateFlow()


    fun getCharacters(page: Int = 1) {
        viewModelScope.launch(Dispatchers.IO) {
            cartoonRepository.getCharacters(page).collect { pagingData ->
                _charactersState.value = pagingData

            }

        }
    }
}