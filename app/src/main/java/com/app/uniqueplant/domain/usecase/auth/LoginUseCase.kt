package com.app.uniqueplant.domain.usecase.auth

import com.app.uniqueplant.data.repository.AuthRepository
import com.app.uniqueplant.domain.model.Resource
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(email: String, password: String): Flow<Resource<AuthResult>> {
        return authRepository.loginUser(email, password)
    }
}
