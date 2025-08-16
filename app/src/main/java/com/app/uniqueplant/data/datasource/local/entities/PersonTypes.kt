package com.app.uniqueplant.data.datasource.local.entities

enum class PersonTypes {
    CUSTOMER,
    DEALER,
    EMPLOYEE;

    companion object {
        fun getDefaultTypes(): List<String> {
            return entries.map { it.name }
        }
    }
}