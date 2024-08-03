package com.example.water_watch_app.data.services

import com.example.water_watch_app.data.models.LoginRequest
import com.example.water_watch_app.data.models.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}
