package com.ssafy.talkeasy.core.data.remote.datasource.chat

import com.google.gson.annotations.SerializedName
import com.ssafy.talkeasy.core.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.talkeasy.core.domain.entity.response.Read

data class ReadResponse(
    @SerializedName("msgId")
    val msgId: String,
    @SerializedName("roomId")
    val roomId: String,
    @SerializedName("readCnt")
    val readCnt: Int,
) : DataToDomainMapper<Read> {

    override fun toDomainModel(): Read = Read(
        roomId = roomId,
        msgId = msgId,
        readCount = readCnt
    )
}