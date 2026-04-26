package com.ssitracker.app.data.local.repository

import com.ssitracker.app.data.local.dao.SSIDao
import com.ssitracker.app.data.local.mapper.toDomain
import com.ssitracker.app.data.local.mapper.toEntity
import com.ssitracker.app.domain.model.SSI
import com.ssitracker.app.domain.repository.SSIRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SSIRepositoryImpl(
    private val dao: SSIDao
) : SSIRepository {

    override suspend fun insertSSI(ssi: SSI) {
        dao.insertSSI(ssi.toEntity())
    }

    override fun getAllSSI(): Flow<List<SSI>> {
        return dao.getAllSSI().map { entities -> entities.map { it.toDomain() } }
    }

    override suspend fun getSSIById(id: String): SSI? {
        return dao.getSSIById(id)?.toDomain()
    }

    override suspend fun deleteSSIById(id: String) {
        dao.deleteSSIById(id)
    }
}