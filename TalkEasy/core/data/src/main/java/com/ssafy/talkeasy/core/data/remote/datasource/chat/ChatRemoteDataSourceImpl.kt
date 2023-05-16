package com.ssafy.talkeasy.core.data.remote.datasource.chat

import com.ssafy.talkeasy.core.data.remote.datasource.common.PagingDefaultResponse
import com.ssafy.talkeasy.core.data.remote.service.ChatApiService
import javax.inject.Inject

class ChatRemoteDataSourceImpl @Inject constructor(
    private val chatApiService: ChatApiService,
) : ChatRemoteDataSource {

    override suspend fun getChatHistory(
        roomId: String,
        offset: Int,
        size: Int,
    ): PagingDefaultResponse<List<ChatResponse>> =
        chatApiService.getChatHistory(roomId, offset, size)
}