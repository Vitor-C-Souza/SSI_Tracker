package com.ssitracker.app.domain.usecase

import com.ssitracker.app.domain.model.SSI
import com.ssitracker.app.domain.repository.SSIRepository

class GetSSIByIdUseCase(
    private val repository: SSIRepository
) {
    suspend operator fun invoke(id: String): SSI? {
        return repository.getSSIById(id)
    }
}