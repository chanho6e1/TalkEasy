package com.ssafy.talkeasy.core.data.remote.repository

import com.ssafy.talkeasy.core.data.common.util.wrapToResource
import com.ssafy.talkeasy.core.data.remote.datasource.fcm.FCMRemoteDataSource
import com.ssafy.talkeasy.core.domain.Resource
import com.ssafy.talkeasy.core.domain.entity.response.Default
import com.ssafy.talkeasy.core.domain.repository.FCMRepository
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers

class FCMRepositoryImpl @Inject constructor(
    private val fcmRemoteDataSource: FCMRemoteDataSource,
) : FCMRepository {

    override suspend fun registerFCMToken(appToken: String): Resource<Default<String>> =
        wrapToResource(Dispatchers.IO) {
            val defaultResponse = fcmRemoteDataSource.registerFCMToken(appToken)
            Default(status = defaultResponse.status, data = defaultResponse.data)
        }
}