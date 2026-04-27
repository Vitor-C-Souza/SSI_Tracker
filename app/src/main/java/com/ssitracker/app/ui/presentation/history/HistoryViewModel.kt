package com.ssitracker.app.ui.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssitracker.app.domain.usecase.GetAllSSIUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val getAllSSIUseCase: GetAllSSIUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(HistoryState())
    val state: StateFlow<HistoryState> = _state

    init {
        getAllSSI()
    }

    private fun getAllSSI() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            try {
                getAllSSIUseCase().take(7).collect { ssi ->
                    _state.update {
                        it.copy(
                            history = ssi,
                            avgScore = ssi.mapNotNull { item -> item.total }.average().toInt(),
                        )
                    }
                }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message) }
            }
        }

    }
}
