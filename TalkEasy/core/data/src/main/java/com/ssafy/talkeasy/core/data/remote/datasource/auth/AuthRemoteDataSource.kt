package com.ssafy.talkeasy.core.data.remote.datasource.auth

import com.ssafy.talkeasy.core.data.remote.datasource.common.DefaultResponse
import com.ssafy.talkeasy.core.domain.entity.request.MemberRequestBody
import java.io.File

interface AuthRemoteDataSource {

    suspend fun requestLogin(accessToken: String, role: Int): DefaultResponse<AuthResponse>

    suspend fun requestJoin(member: MemberRequestBody, image: File?): DefaultResponse<String>
}