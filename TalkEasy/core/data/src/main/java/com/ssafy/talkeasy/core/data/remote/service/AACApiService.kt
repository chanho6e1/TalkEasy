package com.ssafy.talkeasy.core.data.remote.service

import com.ssafy.talkeasy.core.data.remote.datasource.aac.AACWordRequest
import com.ssafy.talkeasy.core.data.remote.datasource.common.DefaultResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AACApiService {

    @POST("/api/aac/generate")
    suspend fun generateSentence(
        @Body
        body: AACWordRequest,
    ): DefaultResponse<String>
}