package com.example.water_watch_app.data.services

import com.example.water_watch_app.data.models.TokenData
import retrofit2.http.GET

interface TokenService {
    @GET("validate-token")
    suspend fun validateToken(): TokenData
}