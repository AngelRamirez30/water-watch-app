package com.example.water_watch_app.data.repositories

import com.example.water_watch_app.data.models.TokenData
import com.example.water_watch_app.data.services.TokenService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenRepository @Inject constructor(private val tokenService: TokenService) {
    suspend fun validateToken(): TokenData = tokenService.validateToken()
}