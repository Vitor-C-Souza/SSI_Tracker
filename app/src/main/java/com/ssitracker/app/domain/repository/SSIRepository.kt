package com.ssitracker.app.domain.repository

import com.ssitracker.app.domain.model.SSI
import kotlinx.coroutines.flow.Flow

interface SSIRepository {
    suspend fun insertSSI(ssi: SSI)
    fun getAllSSI(): Flow<List<SSI>>
    suspend fun getSSIById(id: String): SSI?
    suspend fun deleteSSIById(id: String)
}