package com.app.uniqueplant.domain.usecase

import android.util.Log
import com.app.uniqueplant.data.datasource.local.entities.Category
import com.app.uniqueplant.data.datasource.local.entities.CategoryType
import com.app.uniqueplant.data.datasource.local.entities.RequiredField
import com.app.uniqueplant.data.datasource.local.entities.UnitType
import com.app.uniqueplant.data.datasource.preferences.SharedPreferencesRepository
import com.app.uniqueplant.domain.repository.CategoryRepository

class LoadDefaultsUseCase() {
    suspend fun addDefaultCategories() {

        val defaultCategoryTypes = listOf(
            CategoryType(typeId = 1, name = "Stone Products", description = "Various stone materials for sale"),
            CategoryType(typeId = 2, name = "Installation Services", description = "Stone installation and fitting"),
            CategoryType(typeId = 3, name = "Processing Services", description = "Cutting, polishing and finishing"),
            CategoryType(typeId = 4, name = "Utility Bills", description = "Monthly recurring payments"),
            CategoryType(typeId = 5, name = "Operational Costs", description = "Day-to-day business expenses"),
            CategoryType(typeId = 6, name = "Equipment", description = "Machinery and tools"),
            CategoryType(typeId = 7, name = "Salaries", description = "Employee compensation"),
            CategoryType(typeId = 8, name = "Transportation", description = "Delivery and logistics")
        )

        val defaultUnitTypes = listOf(
            // Area units
            UnitType(unitId = 1, name = "Square Foot", symbol = "sq ft", category = "area", isDefault = true),
            UnitType(unitId = 2, name = "Square Meter", symbol = "sq m", category = "area", isDefault = false),

            // Weight units
            UnitType(unitId = 3, name = "Kilogram", symbol = "kg", category = "weight", isDefault = false),
            UnitType(unitId = 4, name = "Ton", symbol = "ton", category = "weight", isDefault = true),
            UnitType(unitId = 5, name = "Pound", symbol = "lb", category = "weight", isDefault = false),

            // Length units
            UnitType(unitId = 6, name = "Inch", symbol = "in", category = "length", isDefault = false),
            UnitType(unitId = 7, name = "Foot", symbol = "ft", category = "length", isDefault = true),
            UnitType(unitId = 8, name = "Meter", symbol = "m", category = "length", isDefault = false),

            // Thickness units
            UnitType(unitId = 9, name = "Millimeter", symbol = "mm", category = "thickness", isDefault = true),
            UnitType(unitId = 10, name = "Centimeter", symbol = "cm", category = "thickness", isDefault = false)
        )

        val defaultRequiredFields = listOf(
            // Stone Products fields
            RequiredField(fieldId = 1, categoryTypeId = 1, fieldName = "quantity", fieldType = "number", isRequired = true, unitCategoryId = "weight", defaultUnitId = 4),
            RequiredField(fieldId = 2, categoryTypeId = 1, fieldName = "area", fieldType = "measurement", isRequired = true, unitCategoryId = "area", defaultUnitId = 1),
            RequiredField(fieldId = 3, categoryTypeId = 1, fieldName = "unit_price", fieldType = "number", isRequired = true, unitCategoryId = null, defaultUnitId = null),
            RequiredField(fieldId = 4, categoryTypeId = 1, fieldName = "stone_type", fieldType = "text", isRequired = true, unitCategoryId = null, defaultUnitId = null),
            RequiredField(fieldId = 5, categoryTypeId = 1, fieldName = "color", fieldType = "text", isRequired = true, unitCategoryId = null, defaultUnitId = null),
            RequiredField(fieldId = 6, categoryTypeId = 1, fieldName = "thickness", fieldType = "measurement", isRequired = true, unitCategoryId = "thickness", defaultUnitId = 9),
            RequiredField(fieldId = 7, categoryTypeId = 1, fieldName = "finish_type", fieldType = "text", isRequired = false, unitCategoryId = null, defaultUnitId = null),

            // Installation Services fields
            RequiredField(fieldId = 8, categoryTypeId = 2, fieldName = "area_covered", fieldType = "measurement", isRequired = true, unitCategoryId = "area", defaultUnitId = 1),
            RequiredField(fieldId = 9, categoryTypeId = 2, fieldName = "labor_hours", fieldType = "number", isRequired = true, unitCategoryId = null, defaultUnitId = null),
            RequiredField(fieldId = 10, categoryTypeId = 2, fieldName = "number_of_workers", fieldType = "number", isRequired = true, unitCategoryId = null, defaultUnitId = null),
            RequiredField(fieldId = 11, categoryTypeId = 2, fieldName = "location", fieldType = "text", isRequired = true, unitCategoryId = null, defaultUnitId = null),

            // Processing Services fields
            RequiredField(fieldId = 12, categoryTypeId = 3, fieldName = "service_type", fieldType = "text", isRequired = true, unitCategoryId = null, defaultUnitId = null),
            RequiredField(fieldId = 13, categoryTypeId = 3, fieldName = "stone_quantity", fieldType = "measurement", isRequired = true, unitCategoryId = "weight", defaultUnitId = 4),
            RequiredField(fieldId = 14, categoryTypeId = 3, fieldName = "machine_used", fieldType = "text", isRequired = false, unitCategoryId = null, defaultUnitId = null),

            // Utility Bills fields
            RequiredField(fieldId = 15, categoryTypeId = 4, fieldName = "utility_type", fieldType = "text", isRequired = true, unitCategoryId = null, defaultUnitId = null),
            RequiredField(fieldId = 16, categoryTypeId = 4, fieldName = "billing_period", fieldType = "text", isRequired = true, unitCategoryId = null, defaultUnitId = null),
            RequiredField(fieldId = 17, categoryTypeId = 4, fieldName = "due_date", fieldType = "date", isRequired = true, unitCategoryId = null, defaultUnitId = null),
            RequiredField(fieldId = 18, categoryTypeId = 4, fieldName = "account_number", fieldType = "text", isRequired = false, unitCategoryId = null, defaultUnitId = null),

            // Operational Costs fields
            RequiredField(fieldId = 19, categoryTypeId = 5, fieldName = "expense_type", fieldType = "text", isRequired = true, unitCategoryId = null, defaultUnitId = null),
            RequiredField(fieldId = 20, categoryTypeId = 5, fieldName = "department", fieldType = "text", isRequired = false, unitCategoryId = null, defaultUnitId = null),
            RequiredField(fieldId = 21, categoryTypeId = 5, fieldName = "receipt_number", fieldType = "text", isRequired = false, unitCategoryId = null, defaultUnitId = null),

            // Equipment fields
            RequiredField(fieldId = 22, categoryTypeId = 6, fieldName = "equipment_type", fieldType = "text", isRequired = true, unitCategoryId = null, defaultUnitId = null),
            RequiredField(fieldId = 23, categoryTypeId = 6, fieldName = "model", fieldType = "text", isRequired = true, unitCategoryId = null, defaultUnitId = null),
            RequiredField(fieldId = 24, categoryTypeId = 6, fieldName = "serial_number", fieldType = "text", isRequired = false, unitCategoryId = null, defaultUnitId = null),
            RequiredField(fieldId = 25, categoryTypeId = 6, fieldName = "warranty_period", fieldType = "number", isRequired = false, unitCategoryId = null, defaultUnitId = null),

            // Salaries fields
            RequiredField(fieldId = 26, categoryTypeId = 7, fieldName = "employee_name", fieldType = "text", isRequired = true, unitCategoryId = null, defaultUnitId = null),
            RequiredField(fieldId = 27, categoryTypeId = 7, fieldName = "position", fieldType = "text", isRequired = true, unitCategoryId = null, defaultUnitId = null),
            RequiredField(fieldId = 28, categoryTypeId = 7, fieldName = "hours_worked", fieldType = "number", isRequired = false, unitCategoryId = null, defaultUnitId = null),
            RequiredField(fieldId = 29, categoryTypeId = 7, fieldName = "pay_period", fieldType = "text", isRequired = true, unitCategoryId = null, defaultUnitId = null),

            // Transportation fields
            RequiredField(fieldId = 30, categoryTypeId = 8, fieldName = "distance", fieldType = "measurement", isRequired = true, unitCategoryId = "length", defaultUnitId = 7),
            RequiredField(fieldId = 31, categoryTypeId = 8, fieldName = "weight_transported", fieldType = "measurement", isRequired = true, unitCategoryId = "weight", defaultUnitId = 4),
            RequiredField(fieldId = 32, categoryTypeId = 8, fieldName = "vehicle_type", fieldType = "text", isRequired = true, unitCategoryId = null, defaultUnitId = null),
            RequiredField(fieldId = 33, categoryTypeId = 8, fieldName = "fuel_consumed", fieldType = "number", isRequired = false, unitCategoryId = null, defaultUnitId = null)
        )

        val defaultCategories = listOf(
            // Stone Product Categories (Income)
            Category(
                name = "Roda",
                color = 0xFFE57373.toInt(),
                isExpenseCategory = false,
                icon = "🪨",
                description = "Roda type stone products",
                expectedPersonType = "Client",
                categoryTypeId = 1, // Stone Products
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            ),
            Category(
                name = "Bajar",
                color = 0xFF64B5F6.toInt(),
                isExpenseCategory = false,
                icon = "🧱",
                description = "Bajar gravel products",
                expectedPersonType = "Client",
                categoryTypeId = 1, // Stone Products
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            ),
            Category(
                name = "Mix",
                color = 0xFFBA68C8.toInt(),
                isExpenseCategory = false,
                icon = "🏗️",
                description = "Mixed stone aggregates",
                expectedPersonType = "Client",
                categoryTypeId = 1, // Stone Products
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            ),
            Category(
                name = "Scrap",
                color = 0xFF4DB6AC.toInt(),
                isExpenseCategory = false,
                icon = "♻️",
                description = "Stone byproducts and leftover materials",
                expectedPersonType = "Client",
                categoryTypeId = 1, // Stone Products
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            ),
            // Equipment Categories (Expense)
            Category(
                name = "Dumper",
                color = 0xFF81C784.toInt(),
                isExpenseCategory = true,
                icon = "🚚",
                description = "Dumper truck expenses",
                expectedPersonType = "Vendor",
                categoryTypeId = 6, // Equipment
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            ),
            Category(
                name = "Loader",
                color = 0xFFAED581.toInt(),
                isExpenseCategory = true,
                icon = "🚜",
                description = "Front-end loader machinery costs",
                expectedPersonType = "Vendor",
                categoryTypeId = 6, // Equipment
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            ),
            Category(
                name = "Excavator",
                color = 0xFFFFD54F.toInt(),
                isExpenseCategory = true,
                icon = "🏗️",
                description = "Excavator machinery expenses",
                expectedPersonType = "Vendor",
                categoryTypeId = 6, // Equipment
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            ),
            // Operational Categories (Expense)
            Category(
                name = "Plant Maintenance",
                color = 0xFF4FC3F7.toInt(),
                isExpenseCategory = true,
                icon = "🔧",
                description = "Maintenance of crushing plant and equipment",
                expectedPersonType = "Vendor",
                categoryTypeId = 5, // Operational Costs
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            ),
            Category(
                name = "Diesel",
                color = 0xFF9575CD.toInt(),
                isExpenseCategory = true,
                icon = "⛽",
                description = "Fuel for machinery and vehicles",
                expectedPersonType = "Vendor",
                categoryTypeId = 5, // Operational Costs
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            ),
            Category(
                name = "Mobil Oil",
                color = 0xFFF06292.toInt(),
                isExpenseCategory = true,
                icon = "🛢️",
                description = "Lubricants for equipment and machinery",
                expectedPersonType = "Vendor",
                categoryTypeId = 5, // Operational Costs
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            ),
            Category(
                name = "Hassan Khata",
                color = 0xFF4DD0E1.toInt(),
                isExpenseCategory = true,
                icon = "📒",
                description = "Miscellaneous operational expenses",
                expectedPersonType = "Vendor",
                categoryTypeId = 5, // Operational Costs
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            ),
            // Processing Service Category (Expense)
            Category(
                name = "Crushing",
                color = 0xFFA1887F.toInt(),
                isExpenseCategory = true,
                icon = "⚒️",
                description = "Stone crushing and processing services",
                expectedPersonType = "Vendor",
                categoryTypeId = 3, // Processing Services
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            ),
            // Stone Purchase Category (Expense)
            Category(
                name = "Stone Purchase",
                color = 0xFF795548.toInt(),
                isExpenseCategory = true,
                icon = "🪨",
                description = "Raw stone material purchases",
                expectedPersonType = "Vendor",
                categoryTypeId = 1, // Stone Products
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            ),
            // Utility Bill Category (Expense)
            Category(
                name = "Electricity Bill",
                color = 0xFFFF8A65.toInt(),
                isExpenseCategory = true,
                icon = "⚡",
                description = "Monthly electricity expenses",
                expectedPersonType = "Vendor",
                categoryTypeId = 4, // Utility Bills
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            ),
            // Salary Category (Expense)
            Category(
                name = "Salary",
                color = 0xFFDCE775.toInt(),
                isExpenseCategory = true,
                icon = "💰",
                description = "Employee compensation",
                expectedPersonType = "Employee",
                categoryTypeId = 7, // Salaries
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            )
        )
    }


    companion object {
        private const val TAG = "LoadDefaultsUseCase"
    }
}