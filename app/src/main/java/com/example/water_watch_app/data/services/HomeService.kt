package com.example.water_watch_app.data.services

import com.example.water_watch_app.data.models.AllActiveSaaDataItem
import com.example.water_watch_app.data.models.SaaDescriptionData
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeService {
    //SAA means "Sistema de Almacenamiento de Agua" in Spanish
    @GET("get-all-active-saa")
    suspend fun getAllActiveSaa(): List<AllActiveSaaDataItem>

    @GET("get-last-saa-record")
    suspend fun getLastSaaRecord(@Query("saa_id") saaId: Int): SaaDescriptionData
}