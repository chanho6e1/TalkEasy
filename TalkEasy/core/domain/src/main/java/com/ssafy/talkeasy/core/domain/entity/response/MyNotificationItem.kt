package com.ssafy.talkeasy.core.domain.entity.response

data class MyNotificationItem(
    val type: NotificationType,
    val data: String,
    val time: String,
)

enum class NotificationType {
    LOCATION, SOS_REQUEST, SOS_RESPONSE
}