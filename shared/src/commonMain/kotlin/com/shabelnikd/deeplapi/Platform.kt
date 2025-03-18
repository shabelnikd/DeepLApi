package com.shabelnikd.deeplapi

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform