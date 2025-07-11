package com.app.uniqueplant.data.repository

import com.app.uniqueplant.domain.model.Resource
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

interface AuthRepository {
    fun loginUser(email: String, password: String): Flow<Resource<AuthResult>>
    fun signUpUser(name: String, email: String, password: String, userType: String = "employee"): Flow<Resource<AuthResult>>
    fun logout(): Flow<Resource<String>>
    fun getCurrentUser(): FirebaseUser?
    fun isUserLoggedIn(): Boolean

    fun getUserType(): Flow<Resource<String>>
}


