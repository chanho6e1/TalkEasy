package com.ssafy.talkeasy.core.domain.repository

import com.ssafy.talkeasy.core.domain.Resource
import com.ssafy.talkeasy.core.domain.entity.request.MemberRequestBody
import com.ssafy.talkeasy.core.domain.entity.response.Auth
import com.ssafy.talkeasy.core.domain.entity.response.Default
import java.io.File

interface AuthRepository {

    suspend fun requestLogin(accessToken: String, role: Int): Resource<Default<Auth>>

    suspend fun requestJoin(member: MemberRequestBody, image: File?): Resource<Default<String>>
}