package com.ssafy.talkeasy.core.domain.entity

data class AddFollowDetailInfo(
    val userId: String,
    val userName: String,
    val imageUrl: String,
    val gender: Int,
    val birthDate: String,
)