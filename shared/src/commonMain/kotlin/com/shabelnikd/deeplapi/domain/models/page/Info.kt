package com.shabelnikd.deeplapi.domain.models.page

data class Info(
    val count: Int? = null,
    val pages: Int? = null,
    val next: String? = null,
    val prev: String? = ""
)