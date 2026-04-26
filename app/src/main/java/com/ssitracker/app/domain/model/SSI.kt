package com.ssitracker.app.domain.model

import java.util.UUID

data class SSI(
    val id: String = UUID.randomUUID().toString(),
    val total: Float? = null,
    val professionalBrand: Float? = null,
    val findPeople: Float? = null,
    val engageInsights: Float? = null,
    val buildRelationships: Float? = null,
    val note: String? = null,
    val createdAt: Long? = null,
    val updatedAt: Long? = null
)
