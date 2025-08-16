package com.app.uniqueplant.data.datasource.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "unit_types")
data class UnitType(
    @PrimaryKey(autoGenerate = true) val unitId: Int,
    val name: String,
    val symbol: String,
    val category: String,
    val isDefault: Boolean
)
