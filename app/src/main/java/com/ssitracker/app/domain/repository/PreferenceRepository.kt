package com.ssitracker.app.domain.repository

import kotlinx.coroutines.flow.Flow

interface PreferenceRepository {
    val dailyTip: Flow<String?>
    suspend fun saveTip(tip: String, timestamp: Long)
    suspend fun shouldUpdateTip(currentDate: Long): Boolean
}