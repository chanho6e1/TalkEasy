package com.ssafy.talkeasy.core.domain.entity.request

data class MemberRequestBody(
    val accessToken: String,
    val role: Int,
    val name: String,
    val birthDate: String = "",
    val gender: Int = 0,
)