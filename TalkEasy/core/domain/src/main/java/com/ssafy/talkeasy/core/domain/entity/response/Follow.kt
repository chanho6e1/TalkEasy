package com.ssafy.talkeasy.core.domain.entity.response

data class Follow(
    val userId: String,
    val userName: String,
    val imageUrl: String,
    val memo: String,
    val mainStatus: Boolean,
    val locationStatus: Boolean,
)