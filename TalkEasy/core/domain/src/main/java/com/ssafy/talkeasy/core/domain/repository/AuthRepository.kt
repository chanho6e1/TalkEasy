package com.ssafy.talkeasy.core.domain.repository

import com.ssafy.talkeasy.core.domain.Resource
import com.ssafy.talkeasy.core.domain.entity.request.MemberRequestBody
import com.ssafy.talkeasy.core.domain.entity.response.Auth

interface AuthRepository {

    suspend fun requestLogin(accessToken: String): Resource<Auth>

    suspend fun requestJoin(member: MemberRequestBody): Resource<Auth>
}