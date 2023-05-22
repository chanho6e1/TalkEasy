package com.ssafy.talkeasy.core.domain.entity.response

data class Read(
    val msgId: String,
    val roomId: String,
    var readCount: Int,
)