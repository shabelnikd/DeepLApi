package com.shabelnikd.deeplapi.data.core

class NetworkException (val networkError: NetworkError): Throwable(networkError.toString())