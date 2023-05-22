package com.ssafy.talkeasy.core.data.remote.datasource.chat

import com.ssafy.talkeasy.core.data.remote.datasource.common.PagingDefaultResponse

interface ChatRemoteDataSource {

    suspend fun getChatHistory(
        roomId: String,
        offset: Int,
        size: Int,
    ): PagingDefaultResponse<List<ChatResponse>>
}