package com.ssafy.talkeasy.core.data.remote.datasource.follow

import com.google.gson.annotations.SerializedName
import com.ssafy.talkeasy.core.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.talkeasy.core.domain.entity.response.LastChat

data class LastChatResponse(
    @SerializedName("msg")
    val msg: String,
    @SerializedName("created_dt")
    val created_dt: String,
    @SerializedName("readCnt")
    val readCnt: Int,
) : DataToDomainMapper<LastChat> {

    override fun toDomainModel(): LastChat =
        LastChat(message = msg, time = created_dt, readCount = readCnt)
}
