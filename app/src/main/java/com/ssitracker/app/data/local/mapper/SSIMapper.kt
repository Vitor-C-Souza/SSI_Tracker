package com.ssitracker.app.data.local.mapper

import com.ssitracker.app.data.local.entity.SSIEntity
import com.ssitracker.app.domain.model.SSI

fun SSIEntity.toDomain(): SSI = SSI(
    id = id,
    total = total,
    professionalBrand = professionalBrand,
    findPeople = findPeople,
    engageInsights = engageInsights,
    buildRelationships = buildRelationships,
    note = note,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun SSI.toEntity(): SSIEntity = SSIEntity(
    id = id,
    total = total,
    professionalBrand = professionalBrand,
    findPeople = findPeople,
    engageInsights = engageInsights,
    buildRelationships = buildRelationships,
    note = note
)
