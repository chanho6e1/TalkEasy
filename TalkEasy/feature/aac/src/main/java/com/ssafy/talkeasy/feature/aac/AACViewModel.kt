package com.ssafy.talkeasy.feature.aac

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class AACViewModel @Inject constructor() : ViewModel() {

    private val _selectedCard: MutableStateFlow<List<String>> =
        MutableStateFlow(listOf())
    val selectedCard: StateFlow<List<String>> = _selectedCard

    private val _category: MutableStateFlow<String> = MutableStateFlow("")
    val category: StateFlow<String> = _category

    fun addCard(word: String) {
        val mutableList: MutableList<String> =
            if (selectedCard.value.isEmpty()) {
                mutableListOf()
            } else {
                MutableList(selectedCard.value.size) { index -> selectedCard.value[index] }
            }
        mutableList.add(word)
        _selectedCard.value = mutableList
        Log.d("selectedCard", "[ADD] " + selectedCard.value.toString())
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
        Log.d("selectedCard", "[DELETE] " + selectedCard.value.toString())
    }

    fun setCategory(category: String = "") {
        _category.value = category
    }
}