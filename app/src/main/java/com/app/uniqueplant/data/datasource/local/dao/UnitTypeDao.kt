package com.app.uniqueplant.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.uniqueplant.data.datasource.local.entities.UnitType
import kotlinx.coroutines.flow.Flow

@Dao
interface UnitTypeDao {
    @Query("SELECT * FROM unit_types WHERE category = :category")
    fun getUnitsByCategory(category: String): Flow<List<UnitType>>

    @Insert
    suspend fun insertAll(units: List<UnitType>)

    // Additional methods
}