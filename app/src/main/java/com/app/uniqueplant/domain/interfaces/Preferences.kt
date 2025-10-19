package com.app.uniqueplant.domain.interfaces

interface Preferences {
    fun saveInt(key: String, value: Int)
    fun getInt(key: String, defaultValue: Int): Int
}