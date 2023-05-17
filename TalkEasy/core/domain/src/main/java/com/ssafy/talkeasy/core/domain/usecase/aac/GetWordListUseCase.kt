package com.ssafy.talkeasy.core.domain.usecase.aac

import com.ssafy.talkeasy.core.domain.Resource
import com.ssafy.talkeasy.core.domain.entity.response.AACWordList
import com.ssafy.talkeasy.core.domain.repository.AACRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetWordListUseCase @Inject constructor(
    private val aacRepository: AACRepository,
) {

    suspend operator fun invoke(categoryId: Int): Resource<AACWordList> =
        withContext(Dispatchers.IO) {
            aacRepository.getWordList(categoryId)
        }
}