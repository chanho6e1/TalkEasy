package com.ssafy.talkeasy.core.data.remote.datasource.chat

import com.google.gson.annotations.SerializedName
import com.ssafy.talkeasy.core.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.talkeasy.core.domain.entity.response.Chat

data class ChatResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("roomId")
    val roomId: String,
    @SerializedName("msg")
    val msg: String,
    @SerializedName("created_dt")
    val created_dt: String,
    @SerializedName("readCnt")
    val readCnt: Int,
    @SerializedName("toUserId")
    val toUserId: String,
    @SerializedName("fromUserId")
    val fromUserId: String,
    @SerializedName("imageUrl")
    val imageUrl: String?,
    @SerializedName("type")
    val type: Int,
    @SerializedName("status")
    val status: Int,
) : DataToDomainMapper<Chat> {

    override fun toDomainModel(): Chat = Chat(
        roomId = roomId,
        message = msg,
        time = created_dt,
        readCount = readCnt,
        toUserId = toUserId,
        fromUserId = fromUserId,
        type = type,
        status = status
    )
}