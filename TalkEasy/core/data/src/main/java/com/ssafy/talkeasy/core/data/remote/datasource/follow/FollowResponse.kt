package com.ssafy.talkeasy.core.data.remote.datasource.follow

import com.google.gson.annotations.SerializedName
import com.ssafy.talkeasy.core.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.talkeasy.core.domain.entity.response.Follow

data class FollowResponse(
    @SerializedName("userId")
    val userId: String,
    @SerializedName("followId")
    val followId: String,
    @SerializedName("userName")
    val userName: String,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("memo")
    val memo: String,
    @SerializedName("mainStatus")
    val mainStatus: Boolean,
    @SerializedName("age")
    val age: Int?,
    @SerializedName("gender")
    val gender: Int?,
    @SerializedName("birthDate")
    val birthDate: String?,
    @SerializedName("locationStatus")
    val locationStatus: Boolean,
    @SerializedName("nickName")
    val nickName: String,
) : DataToDomainMapper<Follow> {

    override fun toDomainModel(): Follow =
        Follow(
            userId = userId,
            followId = followId,
            userName = userName,
            imageUrl = imageUrl,
            memo = memo,
            mainStatus = mainStatus,
            age = age,
            birthDate = birthDate,
            locationStatus = locationStatus,
            gender = gender,
            nickName = nickName
        )
}