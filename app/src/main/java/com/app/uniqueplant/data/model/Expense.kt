package com.app.uniqueplant.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "expenses",
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["categoryId"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.NO_ACTION
        ),
        ForeignKey(
            entity = Person::class,
            parentColumns = ["personId"],
            childColumns = ["personId"],
            onDelete = ForeignKey.SET_NULL
        )

    ],
    indices = [Index("categoryId"), Index("userId"), Index("personId")],
)
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val expenseId: Long = 0,
    val amount: Double,
    val description: String,
    val date: Date,
    val categoryId: Long,
    val userId: String,
    val personId: Long? = null, // Nullable if not associated with a person
    val paymentMethod: String? = null,
    val location: String? = null,
    val receipt: String? = null, // URL to receipt image
    val isRecurring: Boolean = false,
    val recurringFrequency: String? = null, // daily, weekly, monthly, yearly
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)