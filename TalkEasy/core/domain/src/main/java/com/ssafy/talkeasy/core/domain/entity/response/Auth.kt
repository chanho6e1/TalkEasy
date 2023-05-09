package com.ssafy.talkeasy.core.domain.entity.response

data class Auth(
    val jwt: String,
    val name: String,
)
