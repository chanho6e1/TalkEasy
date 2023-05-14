package com.ssafy.talkeasy.feature.aac

import androidx.lifecycle.ViewModel
import com.ssafy.talkeasy.core.domain.entity.response.Chat
import com.ssafy.talkeasy.core.domain.entity.response.Follow
import com.ssafy.talkeasy.feature.common.SharedPreferences
import com.ssafy.talkeasy.feature.common.util.ChatMode
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class AACViewModel @Inject constructor(private val sharedPreferences: SharedPreferences) :
    ViewModel() {

    private val _selectedCard: MutableStateFlow<List<String>> =
        MutableStateFlow(listOf())
    val selectedCard: StateFlow<List<String>> = _selectedCard

    private val _category: MutableStateFlow<String> = MutableStateFlow("")
    val category: StateFlow<String> = _category

    private val _chatMode: MutableStateFlow<ChatMode> = MutableStateFlow(ChatMode.TTS)
    val chatMode: StateFlow<ChatMode> = _chatMode

    private val _chatPartner: MutableStateFlow<Follow?> = MutableStateFlow(null)
    val chatPartner: StateFlow<Follow?> = _chatPartner

    private val _chats: MutableStateFlow<List<Chat>?> = MutableStateFlow(listOf())
    val chats: StateFlow<List<Chat>?> = _chats

    val whoRequest: String = "위치 정보 요청한 사람"

    fun addCard(word: String) {
        val mutableList: MutableList<String> =
            if (selectedCard.value.isEmpty()) {
                mutableListOf()
            } else {
                MutableList(selectedCard.value.size) { index -> selectedCard.value[index] }
            }
        mutableList.add(word)
        _selectedCard.value = mutableList
    }

    fun deleteCard(word: String) {
        val mutableList: MutableList<String> =
            if (selectedCard.value.isEmpty()) {
                mutableListOf()
            } else {
                MutableList(selectedCard.value.size) { index -> selectedCard.value[index] }
            }
        mutableList.remove(word)
        _selectedCard.value = mutableList
    }

    fun setCategory(category: String = "") {
        _category.value = category
    }

    fun getOnRight() = sharedPreferences.onRight

    fun setOnRight(value: Boolean) {
        sharedPreferences.onRight = value
    }

    fun setChatMode(mode: ChatMode) {
        _chatMode.value = mode
    }
}