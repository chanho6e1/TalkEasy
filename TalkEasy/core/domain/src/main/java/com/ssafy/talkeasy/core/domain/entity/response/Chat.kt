package com.ssafy.talkeasy.core.domain.entity.response

data class Chat(
    val roomId: String,
    val message: String,
    val time: String,
    val readCount: Int,
    val toUserId: String,
    val fromUserId: String,
    val type: Int,
    val status: Int? = null,
)