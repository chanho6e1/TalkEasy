package com.ssafy.talkeasy.feature.chat.ui.tablet

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.talkeasy.core.domain.Resource
import com.ssafy.talkeasy.core.domain.entity.response.Chat
import com.ssafy.talkeasy.core.domain.entity.response.PagingDefault
import com.ssafy.talkeasy.core.domain.usecase.chat.GetChatHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getChatHistoryUseCase: GetChatHistoryUseCase,
) : ViewModel() {

    private val _chatsTotalPage: MutableStateFlow<Int> = MutableStateFlow(0)
    val chatsTotalPage: StateFlow<Int> = _chatsTotalPage

    private val _chats: MutableStateFlow<List<Chat>?> = MutableStateFlow(listOf())
    val chats: StateFlow<List<Chat>?> = _chats

    fun getChatHistory(roomId: String = "645b7a87019c7f5131d179b1", offset: Int, size: Int) =
        viewModelScope.launch {
            when (val value = getChatHistoryUseCase(roomId, offset, size)) {
                is Resource.Success<PagingDefault<List<Chat>>> -> {
                    _chats.value = value.data.data
                    _chatsTotalPage.value = value.data.totalPages
                }

                is Resource.Error -> {
                    Log.e("getChatHistory", "getChatHistory: ${value.errorMessage}")
                }
            }
        }
}