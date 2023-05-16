package com.ssafy.talkeasy.core.domain.repository

import com.ssafy.talkeasy.core.domain.Resource

interface AACRepository {

    suspend fun generateSentence(text: String): Resource<String>
}