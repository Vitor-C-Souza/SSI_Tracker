package com.ssitracker.app.ui.presentation.home

import com.ssitracker.app.domain.model.SSI

data class HomeState(
    val ssiList: List<SSI>? = emptyList(),
    val isLoading: Boolean? = false,
    val error: String? = null
)
