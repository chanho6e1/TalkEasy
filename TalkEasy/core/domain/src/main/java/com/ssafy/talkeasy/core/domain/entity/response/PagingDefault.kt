package com.ssafy.talkeasy.core.domain.entity.response

data class PagingDefault<T>(
    val status: Int,
    val data: T,
    val totalPages: Int,
)