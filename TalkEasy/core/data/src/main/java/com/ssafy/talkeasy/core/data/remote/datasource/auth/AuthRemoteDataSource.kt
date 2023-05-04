package com.ssafy.talkeasy.core.data.remote.datasource.auth

import com.ssafy.talkeasy.core.data.remote.datasource.common.DefaultResponse
import com.ssafy.talkeasy.core.domain.entity.request.MemberRequestBody

interface AuthRemoteDataSource {

    suspend fun requestLogin(accessToken: String): DefaultResponse<String>

    suspend fun requestJoin(member: MemberRequestBody): DefaultResponse<String>
}