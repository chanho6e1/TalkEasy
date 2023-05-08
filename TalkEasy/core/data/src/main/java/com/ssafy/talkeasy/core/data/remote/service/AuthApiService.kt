package com.ssafy.talkeasy.core.data.remote.service

import com.ssafy.talkeasy.core.data.remote.datasource.common.DefaultResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface AuthApiService {

    @POST("/api/oauth/login")
    suspend fun requestLogin(
        @Query("accessToken")
        accessToken: String,
        @Query("role")
        role: Int,
    ): DefaultResponse<String>

    @Multipart
    @POST("/api/oauth/register")
    suspend fun requestJoin(
        @Part("value")
        member: RequestBody,
        @Part
        multipartFile: MultipartBody.Part?,
    ): DefaultResponse<String>
}