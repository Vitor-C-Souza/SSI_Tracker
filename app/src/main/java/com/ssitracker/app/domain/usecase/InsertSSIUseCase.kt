package com.ssitracker.app.domain.usecase

import com.ssitracker.app.domain.model.SSI
import com.ssitracker.app.domain.repository.SSIRepository

class InsertSSIUseCase(
    private val repository: SSIRepository
) {
    suspend operator fun invoke(ssi: SSI) {
        repository.insertSSI(ssi)
    }
}