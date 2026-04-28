package com.ssitracker.app.domain.usecase

import com.ssitracker.app.data.local.entity.SSIEntity
import com.ssitracker.app.data.local.mapper.toDomain
import com.ssitracker.app.domain.repository.AiRepository
import com.ssitracker.app.domain.repository.PreferenceRepository

class GetDailyTipUseCase(
    private val aiRepository: AiRepository,
    private val preferenceRepository: PreferenceRepository
) {
    suspend operator fun invoke(latestSSI: SSIEntity?): String? {
        if (latestSSI == null) return null

        val today = System.currentTimeMillis()

        return if (preferenceRepository.shouldUpdateTip(today)) {
            val newTip = aiRepository.getDailyTip(latestSSI.toDomain())

            if (newTip != null) {
                preferenceRepository.saveTip(newTip, today)
                newTip
            } else {
                null
            }
        } else {
            null
        }
    }
}