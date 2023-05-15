package com.ssafy.talkeasy.core.domain.entity.request

data class MessageRequest(
    val roomId: String,
    val msg: String,
    val toUserId: String,
    val fromUserId: String,
    val type: Int,
    val status: Int = 5,
)