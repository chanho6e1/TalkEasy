package com.ssafy.talkeasy.core.data.remote.datasource.aac

import com.ssafy.talkeasy.core.data.remote.datasource.common.DefaultResponse

interface AACRemoteDataSource {

    suspend fun generateSentence(body: AACWordRequest): DefaultResponse<String>
}