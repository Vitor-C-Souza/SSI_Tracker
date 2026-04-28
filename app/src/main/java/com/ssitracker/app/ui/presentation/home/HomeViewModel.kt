package com.ssitracker.app.ui.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssitracker.app.data.local.datastore.ThemeManager
import com.ssitracker.app.domain.repository.PreferenceRepository
import com.ssitracker.app.domain.usecase.GetAllSSIUseCase
import com.ssitracker.app.domain.usecase.GetDailyTipUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val themeManager: ThemeManager,
    private val getDailyTipUseCase: GetDailyTipUseCase,
    private val preferenceRepository: PreferenceRepository,
    private val getAllSSIUseCase: GetAllSSIUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    val isDarkMode = themeManager.isDarkMode

    init {
        getAllSSI()
    }

    fun toggleTheme() {
        viewModelScope.launch {
            themeManager.toggleTheme()
        }
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

                // Automatically try to update tip if we have data
                if (ssiList.isNotEmpty()) {
                    updateDailyTip()
                }
            }
        }
    }

    val dailyTip = preferenceRepository.dailyTip.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = "Loading daily tip"
    )

    fun updateDailyTip() {
        viewModelScope.launch {
            getDailyTipUseCase()
        }
    }
}
