package com.ssafy.talkeasy.core.data.remote.datasource.member

import com.google.gson.annotations.SerializedName
import com.ssafy.talkeasy.core.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.talkeasy.core.domain.entity.response.MemberInfo

data class MemberInfoResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("userName")
    val userName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("role")
    val role: Int,
    @SerializedName("age")
    val age: Int?,
    @SerializedName("gender")
    val gender: Int?,
    @SerializedName("birthDate")
    val birthDate: String?,
) : DataToDomainMapper<MemberInfo> {

    override fun toDomainModel(): MemberInfo = MemberInfo(
        userId = id,
        userName = userName,
        email = email,
        imageUrl = imageUrl,
        role = role,
        age = age,
        gender = gender,
        birthDate = birthDate
    )
}