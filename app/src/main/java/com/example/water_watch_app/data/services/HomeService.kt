package com.example.water_watch_app.data.services

import com.example.water_watch_app.data.models.HomeData
import retrofit2.http.GET

interface HomeService {
    @GET("get-home")
    suspend fun getHomeData(): HomeData
}