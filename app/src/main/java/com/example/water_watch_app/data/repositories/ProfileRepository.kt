package com.example.water_watch_app.data.repositories


import com.example.water_watch_app.data.models.ProfileData
import com.example.water_watch_app.data.services.ProfileService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor(private val profileService: ProfileService) {
    suspend fun getProfileData(): ProfileData = profileService.getProfileData()
}