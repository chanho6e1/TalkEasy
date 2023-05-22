package com.ssafy.talkeasy.core.domain.entity.response

data class MemberInfo(
    val userId: String,
    val userName: String,
    val email: String,
    val imageUrl: String = "",
    val role: Int,
    val age: Int?,
    val gender: Int?,
    val birthDate: String?,
)