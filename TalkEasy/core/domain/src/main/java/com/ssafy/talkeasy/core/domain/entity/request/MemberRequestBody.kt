package com.ssafy.talkeasy.core.domain.entity.request

data class MemberRequestBody(
    val accessToken: String,
    val age: Int = 0,
    val birthDate: String = "",
    val email: String = "",
    val gender: Int = 0,
    val imageUrl: String,
    val name: String,
    val role: Int = 0,
)