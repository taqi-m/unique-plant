package com.app.uniqueplant.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val userId: String,
    val username: String,
    val email: String,
    val userType: String,
    val passwordHash: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val profilePictureUrl: String? = null,
    val currency: String = "USD",
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
    val lastLoginAt: Date? = null
)