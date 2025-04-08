package com.shabelnikd.deeplapi.domain.models.page

data class BaseResponse(
    val info: Info? = null,
    val results: List<Character>? = null
)