package com.ssitracker.app.ui.presentation.addentry

data class AddEntryState(
    val isLoading: Boolean = false,
    val isSuccessful: Boolean = false,
    val error: String? = null,
    val total: Float? = null,
    val professionalBrand: Float? = null,
    val findPeople: Float? = null,
    val engageInsights: Float? = null,
    val buildRelationships: Float? = null
)

sealed class AddEntryEvent {
    data class TotalChanged(val total: String) : AddEntryEvent()
    data class ProfessionalBrandChanged(val professionalBrand: String) : AddEntryEvent()
    data class FindPeopleChanged(val findPeople: String) : AddEntryEvent()
    data class EngageInsightsChanged(val engageInsights: String) : AddEntryEvent()
    data class BuildRelationshipsChanged(val buildRelationships: String) : AddEntryEvent()
    object SaveEntry : AddEntryEvent()
}
