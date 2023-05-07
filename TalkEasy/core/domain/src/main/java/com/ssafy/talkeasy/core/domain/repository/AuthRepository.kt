package com.ssafy.talkeasy.core.domain.repository

import com.ssafy.talkeasy.core.domain.Resource
import com.ssafy.talkeasy.core.domain.entity.request.MemberRequestBody
import com.ssafy.talkeasy.core.domain.entity.response.Default
import java.io.File

interface AuthRepository {

    suspend fun requestLogin(accessToken: String): Resource<Default<String>>

    suspend fun requestJoin(member: MemberRequestBody, image: File?): Resource<Default<String>>
}