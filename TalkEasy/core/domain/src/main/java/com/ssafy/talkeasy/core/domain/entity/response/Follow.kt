package com.ssafy.talkeasy.core.domain.entity.response

data class Follow(
    val userId: String,
    val userName: String,
    val followId: String,
    val imageUrl: String,
    val memo: String,
    val mainStatus: Boolean,
    val gender: Int?,
    val age: Int?,
    val birthDate: String?,
    val locationStatus: Boolean,
    val nickName: String,
    val roomId: String,
    val lastChat: LastChat?,
)