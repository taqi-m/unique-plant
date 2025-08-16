package com.app.uniqueplant.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.uniqueplant.data.datasource.local.entities.TransactionFieldValue
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionFieldValueDao {
    @Query("SELECT * FROM transaction_field_values WHERE transactionId = :transactionId AND transactionType = :transactionType")
    fun getValuesForTransaction(transactionId: Int, transactionType: String): Flow<List<TransactionFieldValue>>

    @Insert
    suspend fun insertAll(values: List<TransactionFieldValue>)

    // Additional methods
}