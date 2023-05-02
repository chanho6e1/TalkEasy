package com.ssafy.talkeasy.core.data.remote.service

import com.ssafy.talkeasy.core.data.remote.datasource.auth.AuthResponse
import com.ssafy.talkeasy.core.domain.entity.request.MemberRequestBody
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApiService {

    @POST("/api/oauth/login")
    suspend fun requestLogin(
        @Query("accessToken")
        accessToken: String,
    ): AuthResponse

    @POST("/api/oauth/register")
    suspend fun requestJoin(
        @Body
        member: MemberRequestBody,
    ): AuthResponse
}