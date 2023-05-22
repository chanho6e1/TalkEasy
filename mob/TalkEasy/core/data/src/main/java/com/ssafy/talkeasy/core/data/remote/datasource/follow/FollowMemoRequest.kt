package com.ssafy.talkeasy.core.data.remote.datasource.follow

import com.google.gson.annotations.SerializedName

data class FollowMemoRequest(
    @SerializedName("memo")
    val memo: String,
)