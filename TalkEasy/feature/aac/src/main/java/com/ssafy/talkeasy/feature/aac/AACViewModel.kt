package com.ssafy.talkeasy.feature.aac

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.talkeasy.core.domain.Resource
import com.ssafy.talkeasy.core.domain.entity.response.AACWord
import com.ssafy.talkeasy.core.domain.entity.response.AACWordList
import com.ssafy.talkeasy.core.domain.entity.response.Follow
import com.ssafy.talkeasy.core.domain.usecase.aac.GenerateSentenceUseCase
import com.ssafy.talkeasy.core.domain.usecase.aac.GetRelativeVerbListUseCase
import com.ssafy.talkeasy.core.domain.usecase.aac.GetWordListUseCase
import com.ssafy.talkeasy.feature.common.SharedPreferences
import com.ssafy.talkeasy.feature.common.util.ChatMode
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AACViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val generateSentenceUseCase: GenerateSentenceUseCase,
    private val getWordListUseCase: GetWordListUseCase,
    private val getRelativeVerbListUseCase: GetRelativeVerbListUseCase,
) : ViewModel() {

    private val _selectedCard: MutableStateFlow<List<String>> =
        MutableStateFlow(listOf())
    val selectedCard: StateFlow<List<String>> = _selectedCard

    private val _category: MutableStateFlow<String> = MutableStateFlow("")
    val category: StateFlow<String> = _category

    private val _chatMode: MutableStateFlow<ChatMode> = MutableStateFlow(ChatMode.TTS)
    val chatMode: StateFlow<ChatMode> = _chatMode

    private val _chatPartner: MutableStateFlow<Follow?> = MutableStateFlow(null)
    val chatPartner: StateFlow<Follow?> = _chatPartner

    private val _generatedSentence: MutableStateFlow<String> = MutableStateFlow("")
    val generatedSentence: StateFlow<String> = _generatedSentence

    private val _aacFixedList: MutableStateFlow<List<AACWord>> = MutableStateFlow(listOf())
    val aacFixedList: StateFlow<List<AACWord>> = _aacFixedList

    private val _aacWordList: MutableStateFlow<AACWordList?> = MutableStateFlow(null)
    val aacWordList: StateFlow<AACWordList?> = _aacWordList

    private val _relativeVerbList: MutableStateFlow<List<AACWord>> = MutableStateFlow(listOf())
    val relativeVerbList: StateFlow<List<AACWord>> = _relativeVerbList

    val whoRequest: String = "위치 정보 요청한 사람"

    fun initSelectedCard() {
        _selectedCard.value = listOf()
    }

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

    fun deleteCard(removeItemIndex: Int) {
        val mutableList: MutableList<String> =
            if (selectedCard.value.isEmpty()) {
                mutableListOf()
            } else {
                MutableList(selectedCard.value.size) { index -> selectedCard.value[index] }
            }
        mutableList.removeAt(removeItemIndex)
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

    fun setChatPartner(chatPartner: Follow?) {
        _chatPartner.value = chatPartner
    }

    fun initAACWordList() {
        _aacWordList.value = null
    }

    fun initRelativeVerbList() {
        _relativeVerbList.value = listOf()
    }

    fun generateSentence(words: List<String>) = viewModelScope.launch {
        val text = words.toString().split("[", "]")[1]

        when (val value = generateSentenceUseCase(text)) {
            is Resource.Success<String> -> {
                _generatedSentence.value = value.data
            }

            is Resource.Error -> {
                Log.e("generateSentence", "generateSentence: ${value.errorMessage}")
            }
        }
    }

    fun getWordList(categoryId: Int) = viewModelScope.launch {
        if (categoryId != 1) delay(300L)

        when (val value = getWordListUseCase(categoryId)) {
            is Resource.Success<AACWordList> -> {
                if (categoryId == 1) {
                    _aacFixedList.value = value.data.fixedList
                } else {
                    _aacWordList.value = value.data
                }
            }

            is Resource.Error -> {
                Log.e("getWordList", "getWordList: ${value.errorMessage}")
            }
        }
    }

    fun getRelativeVerbList(aacId: Int) = viewModelScope.launch {
        when (val value = getRelativeVerbListUseCase(aacId)) {
            is Resource.Success<List<AACWord>> -> {
                _relativeVerbList.value = value.data
            }

            is Resource.Error -> {
                Log.e("getRelativeVerbList", "getRelativeVerbList: ${value.errorMessage}")
            }
        }
    }
}