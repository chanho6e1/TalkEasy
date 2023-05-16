package com.ssafy.talkeasy.feature.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.talkeasy.core.domain.Resource
import com.ssafy.talkeasy.core.domain.entity.request.MessageRequest
import com.ssafy.talkeasy.core.domain.entity.request.ReadMessageRequest
import com.ssafy.talkeasy.core.domain.entity.response.Chat
import com.ssafy.talkeasy.core.domain.entity.response.PagingDefault
import com.ssafy.talkeasy.core.domain.usecase.chat.DisConnectRabbitmqUseCase
import com.ssafy.talkeasy.core.domain.usecase.chat.GetChatHistoryUseCase
import com.ssafy.talkeasy.core.domain.usecase.chat.ReadChatMessageUseCase
import com.ssafy.talkeasy.core.domain.usecase.chat.ReceiveChatMessageUseCase
import com.ssafy.talkeasy.core.domain.usecase.chat.SendChatMessageUseCase
import com.ssafy.talkeasy.core.domain.usecase.chat.StopReceiveMessageUseCase
import com.ssafy.talkeasy.feature.common.util.getCurrentDateTime
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getChatHistoryUseCase: GetChatHistoryUseCase,
    private val sendChatMessageUseCase: SendChatMessageUseCase,
    private val readChatMessageUseCase: ReadChatMessageUseCase,
    private val receiveChatMessageUseCase: ReceiveChatMessageUseCase,
    private val stopReceiveMessageUseCase: StopReceiveMessageUseCase,
    private val disConnectRabbitmqUseCase: DisConnectRabbitmqUseCase,
) : ViewModel() {

    private val _chatsTotalPage: MutableStateFlow<Int> = MutableStateFlow(0)
    val chatsTotalPage: StateFlow<Int> = _chatsTotalPage

    private val _chats: MutableStateFlow<List<Chat>> = MutableStateFlow(listOf())
    val chats: StateFlow<List<Chat>> = _chats

    private val _newChat: MutableStateFlow<Chat?> = MutableStateFlow(null)
    val newChat: StateFlow<Chat?> = _newChat

    fun getChatHistory(roomId: String, offset: Int, size: Int) =
        viewModelScope.launch {
            when (val value = getChatHistoryUseCase(roomId, offset, size)) {
                is Resource.Success<PagingDefault<List<Chat>>> -> {
                    if (offset == 1) {
                        _chats.value = value.data.data
                    } else {
                        addChatsFromChats(value.data.data)
                    }
                    _chatsTotalPage.value = value.data.totalPages
                }

                is Resource.Error -> {
                    Log.e("getChatHistory", "getChatHistory: ${value.errorMessage}")
                }
            }
        }

    fun sendChatMessage(
        type: Int,
        fromUserId: String,
        msg: String,
        roomId: String,
        toUserId: String,
    ) = viewModelScope.launch {
        val message = MessageRequest(
            type = type,
            fromUserId = fromUserId,
            msg = msg,
            roomId = roomId,
            toUserId = toUserId
        )

        message.let {
            when (val value = sendChatMessageUseCase(message)) {
                is Resource.Success<Boolean> -> {
                    if (value.data) {
                        val chat = Chat(
                            roomId = roomId,
                            fromUserId = fromUserId,
                            type = type,
                            message = msg,
                            time = getCurrentDateTime(),
                            readCount = 1,
                            toUserId = toUserId,
                            status = 5
                        )
                        addChatFromChats(chat)
                    }
                }

                is Resource.Error -> {
                    Log.e("sendChatMessage", "sendChatMessage: ${value.errorMessage}")
                }
            }
        }
    }

    fun readChatMessage(roomId: String, readUserId: String, readTime: String) =
        viewModelScope.launch {
            val readChatMessage = ReadMessageRequest(
                roomId = roomId,
                readTime = readTime,
                readUserId = readUserId
            )

            readChatMessage.let {
                when (val value = readChatMessageUseCase(readChatMessage)) {
                    is Resource.Success<Boolean> -> {
                        if (value.data) {
                            readChatLocalData(readUserId)
                        }
                    }

                    is Resource.Error -> {
                        Log.e("readChatMessage", "readChatMessage: ${value.errorMessage}")
                    }
                }
            }
        }

    fun receiveChatMessage(roomId: String, fromUserId: String) =
        viewModelScope.launch {
            receiveChatMessageUseCase(roomId, fromUserId) {
                _newChat.value = it
                addChatFromChats(it)
            }
        }

    fun stopReceiveMessage() = viewModelScope.launch {
        stopReceiveMessageUseCase()
    }

    private fun addChatFromChats(chat: Chat) {
        val newChats: MutableList<Chat> = _chats.value.toMutableList()
        newChats.removeAt(0)
        newChats.add(chat)
        _chats.value = newChats
    }

    private fun addChatsFromChats(chats: List<Chat>) {
        val newChats: MutableList<Chat> = _chats.value.toMutableList()
        newChats.addAll(0, chats)
        _chats.value = newChats
    }

    private fun readChatLocalData(myUserId: String) {
        val newChats: MutableList<Chat> = _chats.value.map { it.copy() }.toMutableList()
        newChats.withIndex().forEach { (index, chat) ->
            if (chat.readCount > 0 && chat.toUserId == myUserId && chat.fromUserId != myUserId) {
                newChats[index] = chat.copy(readCount = chat.readCount - 1)
            }
        }
        _chats.value = newChats
    }

    fun loadMoreChats(roomId: String, offset: Int, size: Int) {
        getChatHistory(roomId, offset, size)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch {
            disConnectRabbitmqUseCase()
        }
    }
}