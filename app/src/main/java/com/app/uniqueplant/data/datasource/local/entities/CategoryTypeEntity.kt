package com.app.uniqueplant.data.datasource.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_types")
data class CategoryType(
    @PrimaryKey(autoGenerate = true) val typeId: Int,
    val name: String,
    val description: String?
)
