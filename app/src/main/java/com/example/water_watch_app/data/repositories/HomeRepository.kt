package com.example.water_watch_app.data.repositories

import com.example.water_watch_app.data.models.HomeData
import com.example.water_watch_app.data.services.HomeService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository @Inject constructor(private val homeService: HomeService) {
    suspend fun getHomeData(): HomeData {
        return homeService.getHomeData()
    }
}