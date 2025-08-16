package com.app.uniqueplant.data.datasource.local

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.app.uniqueplant.data.datasource.preferences.SharedPreferencesRepository
import com.app.uniqueplant.domain.usecase.LoadDefaultsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseCallback @Inject constructor(
    private val loadDefaultsUseCase: LoadDefaultsUseCase,
    private val preferencesRepository: SharedPreferencesRepository
) : RoomDatabase.Callback() {

    companion object {
        private const val PREF_KEY_SEEDED = "db_defaults_seeded"
    }

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        // Run in background
        CoroutineScope(Dispatchers.IO).launch {
            val alreadySeeded = preferencesRepository.getBoolean(PREF_KEY_SEEDED, false)
            if (!alreadySeeded) {
                loadDefaultsUseCase.addDefaultCategories()
                preferencesRepository.putBoolean(PREF_KEY_SEEDED, true)
            }
        }
    }
}
