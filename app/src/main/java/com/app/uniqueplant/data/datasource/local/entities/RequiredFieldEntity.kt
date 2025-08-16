package com.app.uniqueplant.data.datasource.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "required_fields",
    indices = [
        Index(value = ["categoryTypeId"]),
        Index(value = ["defaultUnitId"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = CategoryType::class,
            parentColumns = ["typeId"],
            childColumns = ["categoryTypeId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = UnitType::class,
            parentColumns = ["unitId"],
            childColumns = ["defaultUnitId"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class RequiredField(
    @PrimaryKey(autoGenerate = true) val fieldId: Int,
    val categoryTypeId: Int,
    val fieldName: String,
    val fieldType: String,
    val isRequired: Boolean,
    val unitCategoryId: String?,
    val defaultUnitId: Int?
)
