package com.ssafy.talkeasy.core.data.remote.service

import com.ssafy.talkeasy.core.data.remote.datasource.chat.ChatResponse
import com.ssafy.talkeasy.core.data.remote.datasource.common.PagingDefaultResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ChatApiService {

    @GET("/api/chats/chat-history/{chatRoomId}")
    suspend fun getChatHistory(
        @Path("chatRoomId")
        roomId: String,
        @Query("offset")
        offset: Int,
        @Query("size")
        size: Int,
    ): PagingDefaultResponse<List<ChatResponse>>
}