package com.ssafy.talkeasy.core.domain.entity.request

data class MemberRequestBody(
    val accessToken: String,
    val role: Int,
    val name: String,
    val age: Int = 0,
    val birthDate: String = "",
    val gender: Int = 0,
)