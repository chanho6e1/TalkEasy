package com.ssafy.talkeasy.core.data.remote.datasource.common

import com.google.gson.annotations.SerializedName
import com.ssafy.talkeasy.core.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.talkeasy.core.domain.entity.response.Default

data class DefaultResponse<T>(
    @SerializedName("status")
    val status: Int,
    @SerializedName("data")
    val data: T,
) : DataToDomainMapper<Default<T>> {

    override fun toDomainModel(): Default<T> = Default(status = status, data = data)
}