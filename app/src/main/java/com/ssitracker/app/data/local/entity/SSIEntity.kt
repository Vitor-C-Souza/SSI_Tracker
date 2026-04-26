package com.ssitracker.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ssi_table")
data class SSIEntity(
    @PrimaryKey val id: String,

    val total: Float? = null,

    val professionalBrand: Float? = null,
    val findPeople: Float? = null,
    val engageInsights: Float? = null,
    val buildRelationships: Float? = null,

    val note: String? = null,

    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
