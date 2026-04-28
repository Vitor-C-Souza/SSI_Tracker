package com.ssitracker.app.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.ssitracker.app.domain.repository.PreferenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.Calendar

class PreferenceRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : PreferenceRepository {
    private val LAST_TIP_KEY = stringPreferencesKey("last_tip")
    private val LAST_TIP_DATE_KEY = longPreferencesKey("last_tip_date")

    override val dailyTip: Flow<String?> = dataStore.data.map { it[LAST_TIP_KEY] }

    override suspend fun saveTip(tip: String, timestamp: Long) {
        dataStore.edit { prefs ->
            prefs[LAST_TIP_KEY] = tip
            prefs[LAST_TIP_DATE_KEY] = timestamp
        }
    }

    override suspend fun shouldUpdateTip(currentDate: Long): Boolean {
        val lastDate = dataStore.data.map { it[LAST_TIP_DATE_KEY] ?: 0L }.first()
        if (lastDate == 0L) return true

        return !isSameDay(lastDate, currentDate)
    }

    private fun isSameDay(time1: Long, time2: Long): Boolean {
        val cal1 = Calendar.getInstance().apply { timeInMillis = time1 }
        val cal2 = Calendar.getInstance().apply { timeInMillis = time2 }
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
    }
}