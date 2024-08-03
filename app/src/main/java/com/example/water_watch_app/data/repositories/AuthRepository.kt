package com.example.water_watch_app.data.repositories

import com.example.water_watch_app.data.models.LoginRequest
import com.example.water_watch_app.data.models.LoginResponse
import com.example.water_watch_app.data.services.AuthService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val authService: AuthService
) {
    suspend fun login(username: String, password: String): LoginResponse {
        val request = LoginRequest(username, password)
        return authService.login(request)
    }
}
