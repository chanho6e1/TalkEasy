package com.ssafy.talkeasy.core.data.remote.datasource.aac

import com.google.gson.annotations.SerializedName

data class TTSRequest(
    @SerializedName("text")
    val text: String,
)