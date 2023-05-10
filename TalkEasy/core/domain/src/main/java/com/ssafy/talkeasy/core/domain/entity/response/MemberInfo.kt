package com.ssafy.talkeasy.core.domain.entity.response

data class MemberInfo(
    val id: String,
    val userName: String,
    val email: String,
    val imageUrl: String = "",
    val role: Int,
    val age: Int?,
    val birthDate: String?,
)