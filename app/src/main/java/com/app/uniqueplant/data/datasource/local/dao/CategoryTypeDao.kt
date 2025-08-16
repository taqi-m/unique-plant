package com.app.uniqueplant.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.uniqueplant.data.datasource.local.entities.CategoryType
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryTypeDao {
    @Query("SELECT * FROM category_types")
    fun getAllCategoryTypes(): Flow<List<CategoryType>>

    @Insert
    suspend fun insert(categoryType: CategoryType): Long

    // Additional methods
}