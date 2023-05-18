package com.ssafy.talkeasy.core.domain.entity.request

data class ReadMessageRequest(
    val roomId: String,
    val readUserId: String,
    val readTime: String,
)