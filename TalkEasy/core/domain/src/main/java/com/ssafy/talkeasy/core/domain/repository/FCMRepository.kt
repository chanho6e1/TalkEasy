package com.ssafy.talkeasy.core.domain.repository

import com.ssafy.talkeasy.core.domain.Resource
import com.ssafy.talkeasy.core.domain.entity.response.Default

interface FCMRepository {

    suspend fun registerFCMToken(appToken: String): Resource<Default<String>>
}