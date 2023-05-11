package com.ssafy.talkeasy.core.domain.entity

data class Chat(
    val message: String,
    val time: String,
    val type: Int,
    val status: Int? = null,
)