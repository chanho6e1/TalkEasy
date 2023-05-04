package com.ssafy.talkeasy.core.domain.repository

import com.ssafy.talkeasy.core.domain.Resource
import com.ssafy.talkeasy.core.domain.entity.request.MemberRequestBody
import com.ssafy.talkeasy.core.domain.entity.response.Default

interface AuthRepository {

    suspend fun requestLogin(accessToken: String): Resource<Default<String>>

    suspend fun requestJoin(member: MemberRequestBody): Resource<Default<String>>
}