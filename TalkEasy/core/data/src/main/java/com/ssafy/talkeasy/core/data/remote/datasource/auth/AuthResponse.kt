package com.ssafy.talkeasy.core.data.remote.datasource.auth

import com.google.gson.annotations.SerializedName
import com.ssafy.talkeasy.core.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.talkeasy.core.domain.entity.response.Auth

data class AuthResponse(
    @SerializedName("token")
    val token: String,
    @SerializedName("name")
    val name: String,
) : DataToDomainMapper<Auth> {

    override fun toDomainModel(): Auth = Auth(token = token, name = name)
}