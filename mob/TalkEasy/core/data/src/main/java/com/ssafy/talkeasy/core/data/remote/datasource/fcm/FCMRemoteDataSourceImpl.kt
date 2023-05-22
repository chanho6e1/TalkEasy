package com.ssafy.talkeasy.core.data.remote.datasource.fcm

import com.ssafy.talkeasy.core.data.remote.datasource.common.DefaultResponse
import com.ssafy.talkeasy.core.data.remote.service.FCMApiService
import javax.inject.Inject

class FCMRemoteDataSourceImpl @Inject constructor(
    private val fcmApiService: FCMApiService,
) : FCMRemoteDataSource {

    override suspend fun registerFCMToken(appToken: String): DefaultResponse<String> =
        fcmApiService.registerFCMToken(appToken)
}