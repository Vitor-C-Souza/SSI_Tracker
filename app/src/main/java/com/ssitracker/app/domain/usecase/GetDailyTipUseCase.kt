package com.ssitracker.app.domain.usecase

import com.ssitracker.app.domain.repository.AiRepository
import com.ssitracker.app.domain.repository.PreferenceRepository
import com.ssitracker.app.domain.repository.SSIRepository
import kotlinx.coroutines.flow.first

class GetDailyTipUseCase(
    private val aiRepository: AiRepository,
    private val preferenceRepository: PreferenceRepository,
    private val ssiRepository: SSIRepository
) {
    suspend operator fun invoke(): String? {
        val today = System.currentTimeMillis()

        return if (preferenceRepository.shouldUpdateTip(today)) {
            val ssiHistory = ssiRepository.getAllSSI().first()
            if (ssiHistory.isEmpty()) return null

            val newTip = aiRepository.getDailyTip(ssiHistory)

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