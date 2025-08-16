package com.app.uniqueplant.data.datasource.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "transaction_field_values",
    indices = [
        Index(value = ["transactionId", "transactionType"]),
        Index(value = ["fieldId"]),
        Index(value = ["unitId"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = RequiredField::class,
            parentColumns = ["fieldId"],
            childColumns = ["fieldId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = UnitType::class,
            parentColumns = ["unitId"],
            childColumns = ["unitId"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class TransactionFieldValue(
    @PrimaryKey(autoGenerate = true) val valueId: Int,
    val transactionId: Int,
    val transactionType: String, // "EXPENSE" or "INCOME"
    val fieldId: Int,
    val stringValue: String?,
    val numberValue: Double?,
    val dateValue: Long?,
    val unitId: Int?
)