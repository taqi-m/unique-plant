package com.app.uniqueplant.data.remote.sync

import com.app.uniqueplant.domain.interfaces.SyncService
import javax.inject.Inject

class EnhancedSyncManagerImpl @Inject constructor(
    private val enhancedSyncManager: EnhancedSyncManager
) : SyncService {
    override suspend fun syncAllData() {
        enhancedSyncManager.syncAllData()
    }

    override suspend fun syncCategories() {
        enhancedSyncManager.syncCategories()
    }

    override suspend fun syncPersons() {
        enhancedSyncManager.syncPersons()
    }

    override suspend fun syncExpenses() {
        enhancedSyncManager.syncExpenses()
    }

    override suspend fun syncIncomes() {
        enhancedSyncManager.syncIncomes()
    }
}