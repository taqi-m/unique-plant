package com.app.uniqueplant.data.datasource.local.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "categories",
    indices = [Index("categoryId"),Index(value = ["categoryTypeId"])],
)
data class Category(
    @PrimaryKey(autoGenerate = true)
    val categoryId: Long = 0,
    val name: String,
    val color: Int = 0xFF000000.toInt(),
    val isExpenseCategory: Boolean,
    val icon: String? = null,
    val description: String? = null,
    val expectedPersonType: String? = null,
    val categoryTypeId: Int? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)