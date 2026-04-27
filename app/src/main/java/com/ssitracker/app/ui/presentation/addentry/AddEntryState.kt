package com.ssitracker.app.ui.presentation.addentry

data class AddEntryState(
    val isLoading: Boolean = false,
    val isSuccessful: Boolean = false,
    val error: String? = null,
    val total: Float? = 50f,
    val professionalBrand: Float? = 12.5f,
    val findPeople: Float? = 12.5f,
    val engageInsights: Float? = 12.5f,
    val buildRelationships: Float? = 12.5f
)

sealed class AddEntryEvent {
    data class TotalChanged(val total: String) : AddEntryEvent()
    data class ProfessionalBrandChanged(val professionalBrand: String) : AddEntryEvent()
    data class FindPeopleChanged(val findPeople: String) : AddEntryEvent()
    data class EngageInsightsChanged(val engageInsights: String) : AddEntryEvent()
    data class BuildRelationshipsChanged(val buildRelationships: String) : AddEntryEvent()
    object SaveEntry : AddEntryEvent()
}
