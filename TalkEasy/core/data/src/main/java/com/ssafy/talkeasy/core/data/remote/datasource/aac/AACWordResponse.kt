package com.ssafy.talkeasy.core.data.remote.datasource.aac

import com.google.gson.annotations.SerializedName
import com.ssafy.talkeasy.core.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.talkeasy.core.domain.entity.response.AACWord

data class AACWordResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("category")
    val category: Int,
) : DataToDomainMapper<AACWord> {

    override fun toDomainModel(): AACWord = AACWord(id, title, category)
}
