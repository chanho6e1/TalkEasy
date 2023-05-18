package com.ssafy.talkeasy.core.data.remote.service

import com.ssafy.talkeasy.core.data.remote.datasource.common.DefaultResponse
import retrofit2.http.PUT
import retrofit2.http.Query

interface FCMApiService {

    @PUT("/api/fcm/app-token")
    suspend fun registerFCMToken(
        @Query("appToken")
        appToken: String,
    ): DefaultResponse<String>
}