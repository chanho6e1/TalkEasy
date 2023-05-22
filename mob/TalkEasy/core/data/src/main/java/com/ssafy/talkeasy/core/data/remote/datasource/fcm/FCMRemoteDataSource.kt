package com.ssafy.talkeasy.core.data.remote.datasource.fcm

import com.ssafy.talkeasy.core.data.remote.datasource.common.DefaultResponse

interface FCMRemoteDataSource {

    suspend fun registerFCMToken(appToken: String): DefaultResponse<String>
}