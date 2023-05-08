package com.ssafy.talkeasy.core.domain

sealed class Resource<out T> {
    class Success<out T>(val code: Int, val data: T) : Resource<T>()
    class Error<out T>(val errorMessage: String? = null) : Resource<T>()
}