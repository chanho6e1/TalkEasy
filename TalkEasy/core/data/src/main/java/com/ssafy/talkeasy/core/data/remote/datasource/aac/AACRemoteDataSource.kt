package com.ssafy.talkeasy.core.data.remote.datasource.aac

import com.ssafy.talkeasy.core.data.remote.datasource.common.DefaultResponse
import com.ssafy.talkeasy.core.data.remote.datasource.common.PagingDefaultResponse

interface AACRemoteDataSource {

    suspend fun generateSentence(body: AACWordRequest): DefaultResponse<String>

    suspend fun getWordList(categoryId: Int): PagingDefaultResponse<AACWordListResponse>
}