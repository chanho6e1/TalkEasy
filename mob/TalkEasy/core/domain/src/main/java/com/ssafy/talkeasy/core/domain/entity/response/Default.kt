package com.ssafy.talkeasy.core.domain.entity.response

data class Default<T>(
    val status: Int,
    val data: T,
)