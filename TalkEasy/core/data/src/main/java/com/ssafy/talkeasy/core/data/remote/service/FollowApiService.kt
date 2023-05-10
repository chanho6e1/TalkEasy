package com.ssafy.talkeasy.core.data.remote.service

import com.ssafy.talkeasy.core.data.remote.datasource.common.PagingDefaultResponse
import com.ssafy.talkeasy.core.domain.entity.response.Follow
import retrofit2.http.GET

interface FollowApiService {

    @GET("/api/follows")
    suspend fun requestFollowList(): PagingDefaultResponse<List<Follow>>
}