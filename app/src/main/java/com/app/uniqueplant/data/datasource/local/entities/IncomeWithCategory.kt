package com.app.uniqueplant.data.datasource.local.entities

import androidx.room.Embedded
import androidx.room.Relation

data class IncomeWithCategory(
    @Embedded val income: Income,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "categoryId"
    )
    val category: Category?
)