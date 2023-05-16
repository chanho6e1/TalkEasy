package com.ssafy.talkeasy.core.domain.usecase.aac

import com.ssafy.talkeasy.core.domain.Resource
import com.ssafy.talkeasy.core.domain.repository.AACRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
class GenerateSentenceUseCase @Inject constructor(
    private val aacRepository: AACRepository,
) {

    suspend operator fun invoke(text: String): Resource<String> = withContext(Dispatchers.IO) {
        aacRepository.generateSentence(text)
    }
}