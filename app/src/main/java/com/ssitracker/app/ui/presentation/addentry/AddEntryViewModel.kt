package com.ssitracker.app.ui.presentation.addentry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssitracker.app.domain.model.SSI
import com.ssitracker.app.domain.usecase.InsertSSIUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddEntryViewModel(
    private val insertSSIUseCase: InsertSSIUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(AddEntryState())
    val state = _state.asStateFlow()

    fun onEvent(event: AddEntryEvent) {
        when (event) {
            is AddEntryEvent.TotalChanged -> {
                _state.value = _state.value.copy(total = event.total.toFloatOrNull())
            }

            is AddEntryEvent.ProfessionalBrandChanged -> {
                _state.value =
                    _state.value.copy(professionalBrand = event.professionalBrand.toFloatOrNull())
            }

            is AddEntryEvent.FindPeopleChanged -> {
                _state.value = _state.value.copy(findPeople = event.findPeople.toFloatOrNull())
            }

            is AddEntryEvent.EngageInsightsChanged -> {
                _state.value =
                    _state.value.copy(engageInsights = event.engageInsights.toFloatOrNull())
            }

            is AddEntryEvent.BuildRelationshipsChanged -> {
                _state.value =
                    _state.value.copy(buildRelationships = event.buildRelationships.toFloatOrNull())
            }

            AddEntryEvent.SaveEntry -> {
                saveEntry()
            }
        }
    }

    private fun saveEntry() {
        val total = _state.value.total
        val professionalBrand = _state.value.professionalBrand
        val findPeople = _state.value.findPeople
        val engageInsights = _state.value.engageInsights
        val buildRelationships = _state.value.buildRelationships

        val ssi = SSI(
            total = total,
            professionalBrand = professionalBrand,
            findPeople = findPeople,
            engageInsights = engageInsights,
            buildRelationships = buildRelationships
        )

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            try {
                insertSSIUseCase(ssi)

                _state.update { it.copy(isLoading = false, isSuccessful = true) }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = e.message) }
            }

        }
    }
}
