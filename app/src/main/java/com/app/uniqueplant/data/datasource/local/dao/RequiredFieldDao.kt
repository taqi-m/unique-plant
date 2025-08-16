package com.app.uniqueplant.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.uniqueplant.data.datasource.local.entities.RequiredField
import kotlinx.coroutines.flow.Flow

@Dao
interface RequiredFieldDao {
    @Query("SELECT * FROM required_fields WHERE categoryTypeId = :categoryTypeId")
    fun getFieldsForCategoryType(categoryTypeId: Int): Flow<List<RequiredField>>

    @Insert
    suspend fun insertAll(fields: List<RequiredField>)

    // Additional methods
}