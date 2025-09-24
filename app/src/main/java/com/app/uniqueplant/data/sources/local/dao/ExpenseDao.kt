package com.app.uniqueplant.data.sources.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.MapColumn
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.app.uniqueplant.data.model.ExpenseEntity
import com.app.uniqueplant.data.model.ExpenseFullDbo
import com.app.uniqueplant.data.model.ExpenseWithCategoryDbo
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(expenseEntity: ExpenseEntity): Long
    
    @Update
    suspend fun update(expenseEntity: ExpenseEntity)
    
    @Delete
    suspend fun delete(expenseEntity: ExpenseEntity)

    @Query("SELECT * FROM expenses")
    fun getAllExpenses(): Flow<List<ExpenseEntity>>

    @Query("SELECT * FROM expenses WHERE userId = :userId ORDER BY date DESC")
    fun getAllExpensesByUser(userId: String): Flow<List<ExpenseEntity>>
    
    @Query("SELECT * FROM expenses WHERE expenseId = :id")
    suspend fun getExpenseById(id: Long): ExpenseEntity?
    
    @Transaction
    @Query("SELECT * FROM expenses WHERE userId = :userId ORDER BY date DESC")
    fun getExpensesWithCategory(userId: String): Flow<List<ExpenseWithCategoryDbo>>

    @Transaction
    @Query("SELECT * FROM expenses WHERE expenseId = :id ORDER BY date DESC LIMIT 1")
    suspend fun getSingleFullExpense(id: Long): ExpenseFullDbo

    @Query("""
        SELECT * FROM expenses
        WHERE (:personIds IS NULL OR personId IN (:personIds))
          AND (:categoryIds IS NULL OR categoryId IN (:categoryIds))
          AND (
              (:startDate IS NULL AND :endDate IS NULL)
              OR (:startDate IS NOT NULL AND :endDate IS NULL AND date >= :startDate)
              OR (:startDate IS NULL AND :endDate IS NOT NULL AND date <= :endDate)
              OR (:startDate IS NOT NULL AND :endDate IS NOT NULL AND date BETWEEN :startDate AND :endDate)
          )
    """)
    suspend fun getAllFullExpensesFiltered(
        personIds: List<Long>?,   // pass null to ignore
        categoryIds: List<Long>?, // pass null to ignore
        startDate: Long?,         // nullable → open start
        endDate: Long?            // nullable → open end
    ): List<ExpenseEntity>
    
    @Query("SELECT * FROM expenses WHERE userId = :userId AND date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getExpensesByDateRange(userId: String, startDate: Long, endDate: Long): Flow<List<ExpenseEntity>>

    @Query("SELECT * FROM expenses WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getExpensesByDateRangeForAllUsers(startDate: Long, endDate: Long): Flow<List<ExpenseEntity>>
    
    @Query("SELECT * FROM expenses WHERE userId = :userId AND categoryId = :categoryId ORDER BY date DESC")
    fun getExpensesByCategory(userId: String, categoryId: Long): Flow<List<ExpenseEntity>>
    
    @Query("SELECT SUM(amount) FROM expenses WHERE userId = :userId")
    fun getTotalExpensesByUser(userId: String): Flow<Double?>
    
    @Query("SELECT SUM(amount) FROM expenses WHERE userId = :userId AND date BETWEEN :startDate AND :endDate")
    fun getExpenseSumByDateRange(userId: String, startDate: Long, endDate: Long): Flow<Double?>
    
    @Query("SELECT categoryId, SUM(amount) as total FROM expenses WHERE userId = :userId GROUP BY categoryId ORDER BY total DESC")
    fun getExpenseSumByCategory(userId: String): Flow<Map<@MapColumn("categoryId") Long?, @MapColumn("total") Double>>

    @Query("SELECT COUNT(*) FROM expenses WHERE userId = :userId")
    suspend fun getExpenseCount(userId: String): Int
    @Query("SELECT SUM(amount) FROM expenses WHERE date BETWEEN :startDate AND :endDate")
    fun getExpenseSumByMonth(startDate: Long, endDate: Long): Flow<Double>

    /**
     * Queries related to synchronization with remote server
     */
    @Query("SELECT * FROM expenses WHERE userId = :userId AND needsSync = 1")
    suspend fun getUnsyncedExpenses(userId: String): List<ExpenseEntity>

    @Query("SELECT * FROM expenses WHERE localId = :localId")
    suspend fun getExpenseByLocalId(localId: String): ExpenseEntity?

    @Query("SELECT * FROM expenses WHERE firestoreId = :firestoreId")
    suspend fun getExpenseByFirestoreId(firestoreId: String): ExpenseEntity?

    @Query("""
        UPDATE expenses 
        SET firestoreId = :firestoreId, isSynced = :isSynced, 
            needsSync = 0, lastSyncedAt = :lastSyncedAt 
        WHERE expenseId = :expenseId
    """)
    suspend fun updateSyncStatus(
        expenseId: Long,
        firestoreId: String,
        isSynced: Boolean,
        lastSyncedAt: Long
    )

    @Query("UPDATE expenses SET needsSync = 1 WHERE expenseId = :expenseId")
    suspend fun markForSync(expenseId: Long)

    @Query("UPDATE expenses SET isDeleted = 1, needsSync = 1, updatedAt = :timestamp WHERE expenseId = :expenseId")
    suspend fun markAsDeleted(expenseId: Long, timestamp: Long = System.currentTimeMillis())


    @Query("SELECT COUNT(*) FROM expenses WHERE needsSync = 1")
    fun getUnsyncedExpenseCount(): Flow<Int>

    @Query("SELECT EXISTS(SELECT 1 FROM expenses WHERE needsSync = 1)")
    suspend fun hasUnsyncedData(): Boolean


    /**
     * Sync timestamp queries
     * These help in determining what data needs to be synced
     * between local database and remote server.
     */

    @Query("""
        SELECT MIN(createdAt) 
        FROM expenses 
        WHERE userId = :userId AND needsSync = 1
    """)
    suspend fun getOldestUnsyncedExpenseTimestamp(userId: String): Long?

    @Query("""
        SELECT MAX(lastSyncedAt) 
        FROM expenses 
        WHERE userId = :userId AND lastSyncedAt IS NOT NULL
    """)
    suspend fun getLatestSyncedTimestamp(userId: String): Long?

    @Query("""
        SELECT MAX(updatedAt) 
        FROM expenses 
        WHERE userId = :userId
    """)
    suspend fun getLatestLocalUpdateTimestamp(userId: String): Long?

    @Query("""
        SELECT COUNT(*) 
        FROM expenses 
        WHERE userId = :userId AND updatedAt > :timestamp
    """)
    suspend fun getUpdatedExpensesSince(userId: String, timestamp: Long): Int

}
