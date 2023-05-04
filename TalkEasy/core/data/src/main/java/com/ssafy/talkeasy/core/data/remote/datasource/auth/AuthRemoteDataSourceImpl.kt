package com.ssafy.talkeasy.core.data.remote.datasource.auth

import com.ssafy.talkeasy.core.data.remote.datasource.common.DefaultResponse
import com.ssafy.talkeasy.core.data.remote.service.AuthApiService
import com.ssafy.talkeasy.core.domain.entity.request.MemberRequestBody
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val authApiService: AuthApiService,
) : AuthRemoteDataSource {

    override suspend fun requestLogin(accessToken: String): DefaultResponse<String> =
        authApiService.requestLogin(accessToken)

    override suspend fun requestJoin(member: MemberRequestBody): DefaultResponse<String> =
        authApiService.requestJoin(member)
}