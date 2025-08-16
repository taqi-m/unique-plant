package com.app.uniqueplant.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.uniqueplant.data.datasource.local.dao.CategoryDao
import com.app.uniqueplant.data.datasource.local.dao.CategoryTypeDao
import com.app.uniqueplant.data.datasource.local.dao.ExpenseDao
import com.app.uniqueplant.data.datasource.local.dao.IncomeDao
import com.app.uniqueplant.data.datasource.local.dao.PersonDao
import com.app.uniqueplant.data.datasource.local.dao.RequiredFieldDao
import com.app.uniqueplant.data.datasource.local.dao.TransactionFieldValueDao
import com.app.uniqueplant.data.datasource.local.dao.UnitTypeDao
import com.app.uniqueplant.data.datasource.local.dao.UserDao
import com.app.uniqueplant.data.datasource.local.entities.Category
import com.app.uniqueplant.data.datasource.local.entities.CategoryType
import com.app.uniqueplant.data.datasource.local.entities.Expense
import com.app.uniqueplant.data.datasource.local.entities.Income
import com.app.uniqueplant.data.datasource.local.entities.Person
import com.app.uniqueplant.data.datasource.local.entities.RequiredField
import com.app.uniqueplant.data.datasource.local.entities.TransactionFieldValue
import com.app.uniqueplant.data.datasource.local.entities.UnitType
import com.app.uniqueplant.data.datasource.local.entities.User

@Database(
    entities = [
        User::class,
        Expense::class,
        Income::class,
        Category::class,
        Person::class,
        CategoryType::class,
        UnitType::class,
        RequiredField::class,
        TransactionFieldValue::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun expenseDao(): ExpenseDao
    abstract fun incomeDao(): IncomeDao
    abstract fun categoryDao(): CategoryDao
    abstract fun personDao(): PersonDao
    abstract fun categoryTypeDao(): CategoryTypeDao
    abstract fun unitTypeDao(): UnitTypeDao
    abstract fun requiredFieldDao(): RequiredFieldDao
    abstract fun transactionFieldValueDao(): TransactionFieldValueDao
}