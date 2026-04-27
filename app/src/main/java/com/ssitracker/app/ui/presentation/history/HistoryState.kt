package com.ssitracker.app.ui.presentation.history

import com.ssitracker.app.domain.model.SSI

data class HistoryState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val history: List<SSI>? = emptyList(),
    val avgScore: Int? = null
)
