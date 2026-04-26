package com.ssitracker.app.domain.usecase

import com.ssitracker.app.domain.model.SSI
import com.ssitracker.app.domain.repository.SSIRepository
import kotlinx.coroutines.flow.Flow

class GetAllSSIUseCase(
    private val repository: SSIRepository
) {
    operator fun invoke(): Flow<List<SSI>> {
        return repository.getAllSSI()
    }
}