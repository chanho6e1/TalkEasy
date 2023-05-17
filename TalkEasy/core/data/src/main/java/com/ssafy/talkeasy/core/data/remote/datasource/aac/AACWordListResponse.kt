package com.ssafy.talkeasy.core.data.remote.datasource.aac

import com.google.gson.annotations.SerializedName
import com.ssafy.talkeasy.core.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.talkeasy.core.domain.entity.response.AACWordList

data class AACWordListResponse(
    @SerializedName("fixedList")
    val fixedList: List<AACWordResponse>,
    @SerializedName("aacList")
    val aacList: List<AACWordResponse>,
) : DataToDomainMapper<AACWordList> {

    override fun toDomainModel(): AACWordList =
        AACWordList(
            fixedList = fixedList.map { it.toDomainModel() },
            aacList = aacList.map { it.toDomainModel() }
        )
}