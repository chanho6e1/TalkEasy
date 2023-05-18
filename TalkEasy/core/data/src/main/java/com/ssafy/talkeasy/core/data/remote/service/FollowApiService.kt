package com.ssafy.talkeasy.core.data.remote.service

import com.ssafy.talkeasy.core.data.remote.datasource.common.DefaultResponse
import com.ssafy.talkeasy.core.data.remote.datasource.common.PagingDefaultResponse
import com.ssafy.talkeasy.core.data.remote.datasource.follow.FollowResponse
import com.ssafy.talkeasy.core.data.remote.datasource.follow.NotificationResponse
import retrofit2.http.GET

interface FollowApiService {

    @GET("/api/follows")
    suspend fun requestFollowList(): PagingDefaultResponse<List<FollowResponse>>

    @GET("/api/alarm")
    suspend fun requestNotificationList(): DefaultResponse<List<NotificationResponse>>
}