package com.ssafy.talkeasy.core.data.remote.datasource.auth

import com.google.gson.annotations.SerializedName
import com.ssafy.talkeasy.core.domain.entity.base.DataToDomainMapper
import com.ssafy.talkeasy.core.domain.entity.response.Auth

data class AuthResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: String,
) : DataToDomainMapper<Auth> {

    override fun toDomainModel(): Auth = Auth(message = message, data = data)
}