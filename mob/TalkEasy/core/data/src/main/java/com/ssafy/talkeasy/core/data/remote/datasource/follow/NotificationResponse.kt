package com.ssafy.talkeasy.core.data.remote.datasource.follow

import com.google.gson.annotations.SerializedName
import com.ssafy.talkeasy.core.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.talkeasy.core.domain.entity.response.MyNotificationItem

data class NotificationResponse(
    @SerializedName("alarmId")
    val alarmId: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("readStatus")
    val readStatus: Boolean,
    @SerializedName("chatId")
    val chatId: String,
    @SerializedName("fromUserName")
    val fromUserName: String,
    @SerializedName("created_dt")
    val created_dt: String,
) : DataToDomainMapper<MyNotificationItem> {

    override fun toDomainModel(): MyNotificationItem =
        MyNotificationItem(
            alarmId = alarmId,
            content = content,
            readStatus = readStatus,
            chatId = chatId,
            fromUserName = fromUserName,
            created_dt = created_dt
        )
}