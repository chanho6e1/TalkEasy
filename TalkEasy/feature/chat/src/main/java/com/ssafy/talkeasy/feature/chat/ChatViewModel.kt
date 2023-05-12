package com.ssafy.talkeasy.feature.chat

import androidx.lifecycle.ViewModel
import com.ssafy.talkeasy.core.domain.entity.response.Follow
import com.ssafy.talkeasy.feature.common.util.ChatMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor() : ViewModel() {

    private val _chatMode: MutableStateFlow<ChatMode> = MutableStateFlow(ChatMode.TTS)
    val chatMode: StateFlow<ChatMode> = _chatMode

    private val _chatPartner: MutableStateFlow<Follow?> = MutableStateFlow(null)
    val chatPartner: StateFlow<Follow?> = _chatPartner

    fun setChatMode(value: ChatMode) {
        _chatMode.value = value
    }
}