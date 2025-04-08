package com.shabelnikd.deeplapi.data.models.page

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationDto(
    @SerialName("name")
    val name: String? = null,
    @SerialName("url")
    val url: String? = null
)