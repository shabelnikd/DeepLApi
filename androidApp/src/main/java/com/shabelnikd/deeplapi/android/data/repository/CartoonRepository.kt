package com.shabelnikd.deeplapi.android.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.shabelnikd.deeplapi.android.data.paging.characters.CharacterPageSource
import com.shabelnikd.deeplapi.data.datasource.deeplapi.CartoonApiService
import com.shabelnikd.deeplapi.data.mapper.toDomain
import com.shabelnikd.deeplapi.domain.models.page.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent

class CartoonRepository(
    private val cartoonApiService: CartoonApiService
) : KoinComponent {

    suspend fun getCharacters(page: Int): Flow<PagingData<Character>> {
        val page = Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 3,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                CharacterPageSource(cartoonApiService)
            }
        )

        return page.flow.map { pagingData ->
            pagingData.map { characterDto ->
                characterDto.toDomain()
            }

        }
    }

}