package com.shabelnikd.deeplapi.data.mapper

import com.shabelnikd.deeplapi.data.models.page.CharacterDto
import com.shabelnikd.deeplapi.data.models.page.LocationDto
import com.shabelnikd.deeplapi.data.models.page.OriginDto
import com.shabelnikd.deeplapi.domain.models.page.Character
import com.shabelnikd.deeplapi.domain.models.page.Location
import com.shabelnikd.deeplapi.domain.models.page.Origin

fun CharacterDto.toDomain(): Character {
    return Character(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        origin = origin?.toDomain(),
        location = location?.toDomain(),
        image = image,
        episode = episode,
        url = url,
        created = created
    )
}

fun OriginDto.toDomain(): Origin {
    return Origin(
        name = this.name,
        url = this.url
    )
}

fun LocationDto.toDomain(): Location {
    return Location(
        name = this.name,
        url = this.url
    )
}