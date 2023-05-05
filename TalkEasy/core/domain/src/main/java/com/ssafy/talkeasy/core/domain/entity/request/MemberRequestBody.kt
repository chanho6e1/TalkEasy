package com.ssafy.talkeasy.core.domain.entity.request

import java.io.File

data class MemberRequestBody(
    val accessToken: String,
    val role: Int,
    val name: String,
    val image: File? = null,
    val age: Int = 0,
    val birthDate: String = "",
    val gender: Int = 0,
)