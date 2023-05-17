package com.ssafy.talkeasy.core.domain.repository

import com.ssafy.talkeasy.core.domain.Resource
import com.ssafy.talkeasy.core.domain.entity.response.AACWordList

interface AACRepository {

    suspend fun generateSentence(text: String): Resource<String>

    suspend fun getWordList(categoryId: Int): Resource<AACWordList>
}