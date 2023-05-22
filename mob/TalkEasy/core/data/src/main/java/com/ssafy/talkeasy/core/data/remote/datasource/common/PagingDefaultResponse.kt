package com.ssafy.talkeasy.core.data.remote.datasource.common

import com.google.gson.annotations.SerializedName
import com.ssafy.talkeasy.core.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.talkeasy.core.domain.entity.response.PagingDefault

data class PagingDefaultResponse<T>(
    @SerializedName("status")
    val status: Int,
    @SerializedName("data")
    val data: T,
    @SerializedName("totalPages")
    val totalPages: Int,
) : DataToDomainMapper<PagingDefault<T>> {

    override fun toDomainModel(): PagingDefault<T> =
        PagingDefault(status = status, data = data, totalPages = totalPages)
}