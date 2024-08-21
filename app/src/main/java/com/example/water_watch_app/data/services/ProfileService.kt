package com.example.water_watch_app.data.services

import com.example.water_watch_app.data.models.ProfileData
import retrofit2.http.GET

interface ProfileService {
    @GET("get-profile")
    suspend fun getProfileData(): ProfileData
}