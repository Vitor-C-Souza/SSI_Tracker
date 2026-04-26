package com.ssitracker.app.ui.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssitracker.app.domain.usecase.GetAllSSIUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getAllSSIUseCase: GetAllSSIUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        getAllSSI()
    }

    private fun getAllSSI() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            getAllSSIUseCase().collect { ssiList ->
                _state.update {
                    it.copy(
                        ssiList = ssiList,
                        isLoading = false
                    )
                }
            }
        }
    }
}

