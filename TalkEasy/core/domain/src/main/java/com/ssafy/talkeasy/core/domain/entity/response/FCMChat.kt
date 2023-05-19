package com.ssafy.talkeasy.core.domain.entity.response

data class FCMChat(
    val msgId: String,
    val roomId: String,
    val toUserId: String,
    val fromUserId: String,
    val msg: String, // 메시지 내용
    val created_dt: String, // 생성 시간?
    val type: Int, // 0(msg) :: 1(location) :: 2(sos)
    val status: Int, // 0(REQUEST) :: 1(RESULT) :: 2(REJECT)
    val fromUserName: String,
)