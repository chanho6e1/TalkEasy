package com.ssafy.talkeasy.core.domain.usecase.aac

import com.ssafy.talkeasy.core.domain.Resource
import com.ssafy.talkeasy.core.domain.entity.response.AACWord
import com.ssafy.talkeasy.core.domain.repository.AACRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
class GetRelativeVerbListUseCase @Inject constructor(
    private val aacRepository: AACRepository,
) {

    suspend operator fun invoke(aacId: Int): Resource<List<AACWord>> = withContext(Dispatchers.IO) {
        aacRepository.getRelativeVerbList(aacId)
    }
}