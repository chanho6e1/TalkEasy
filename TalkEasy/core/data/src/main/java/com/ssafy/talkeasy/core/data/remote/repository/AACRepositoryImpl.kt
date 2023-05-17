package com.ssafy.talkeasy.core.data.remote.repository

import com.ssafy.talkeasy.core.data.common.util.wrapToResource
import com.ssafy.talkeasy.core.data.remote.datasource.aac.AACRemoteDataSource
import com.ssafy.talkeasy.core.data.remote.datasource.aac.AACWordRequest
import com.ssafy.talkeasy.core.domain.Resource
import com.ssafy.talkeasy.core.domain.entity.response.AACWordList
import com.ssafy.talkeasy.core.domain.repository.AACRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class AACRepositoryImpl @Inject constructor(
    private val aacRemoteDataSource: AACRemoteDataSource,
) : AACRepository {

    override suspend fun generateSentence(text: String): Resource<String> =
        wrapToResource(Dispatchers.IO) {
            aacRemoteDataSource.generateSentence(AACWordRequest(text)).data
        }

    override suspend fun getWordList(categoryId: Int): Resource<AACWordList> =
        wrapToResource(Dispatchers.IO) {
            aacRemoteDataSource.getWordList(categoryId).data.toDomainModel()
        }
}