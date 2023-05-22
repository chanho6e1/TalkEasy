package com.ssafy.talkeasy.core.domain.usecase.chat

import com.ssafy.talkeasy.core.domain.Resource
import com.ssafy.talkeasy.core.domain.entity.response.Chat
import com.ssafy.talkeasy.core.domain.entity.response.PagingDefault
import com.ssafy.talkeasy.core.domain.repository.ChatRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
class GetChatHistoryUseCase @Inject constructor(
    private val chatRepository: ChatRepository,
) {

    suspend operator fun invoke(
        roomId: String,
        offset: Int,
        size: Int,
    ): Resource<PagingDefault<List<Chat>>> = withContext(Dispatchers.IO) {
        chatRepository.getChatHistory(roomId, offset, size)
    }
}