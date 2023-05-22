package com.ssafy.talkeasy.core.domain.entity.response

data class MyNotificationItem(
    val alarmId: String,
    val content: String,
    val readStatus: Boolean,
    val chatId: String,
    val fromUserName: String,
    val created_dt: String,
)