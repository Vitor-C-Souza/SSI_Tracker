package com.ssitracker.app.domain.repository

import com.ssitracker.app.domain.model.SSI

interface AiRepository {
    suspend fun getDailyTip(ssiHistory: List<SSI>): String?
}